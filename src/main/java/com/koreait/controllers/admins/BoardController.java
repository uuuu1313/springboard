package com.koreait.controllers.admins;

import com.koreait.commons.CommonException;
import com.koreait.commons.MenuDetail;
import com.koreait.commons.Menus;
import com.koreait.commons.constants.Role;
import com.koreait.entities.Board;
import com.koreait.models.board.config.BoardConfigInfoService;
import com.koreait.models.board.config.BoardConfigListService;
import com.koreait.models.board.config.BoardConfigSaveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("AdminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final HttpServletRequest request;
    private final BoardConfigSaveService configSaveService;
    private final BoardConfigInfoService boardconfigInfoService;
    private final BoardConfigListService boardConfigListService;

    /** 게시판 목록 */
    @GetMapping
    public String index(@ModelAttribute BoardSearch boardSearch, Model model){
        commonProcess(model, "게시판 목록");

        Page<Board> data = boardConfigListService.gets(boardSearch);
        model.addAttribute("items", data.getContent());


        return "admin/board/index";
    }

    /**
     * 게시판 등록
     */
    @GetMapping("/register")
    public String register(@ModelAttribute BoardForm boardForm, Model model){
        commonProcess(model, "게시판 등록");

        return "admin/board/config";
    }

    @GetMapping("/{bId}/update")
    public String update(@PathVariable String bId, Model model){
        commonProcess(model, "게시판 수정");

        Board board = boardconfigInfoService.get(bId, true);
        BoardForm boardForm = new ModelMapper().map(board, BoardForm.class);
        boardForm.setMode("update");
        boardForm.setListAccessRole(board.getListAccessRole().toString());
        boardForm.setViewAccessRole(board.getViewAccessRole().toString());
        boardForm.setWriteAccessRole(board.getWriteAccessRole().toString());
        boardForm.setReplyAccessRole(board.getReplyAccessRole().toString());
        boardForm.setCommentAccessRole(board.getCommentAccessRole().toString());

        model.addAttribute("boardForm", boardForm);

        return "admin/board/config";
    }

    @PostMapping("/save")
    private String save(@Valid BoardForm boardForm, Errors errors, Model model){
        String mode = boardForm.getMode();
        commonProcess(model, mode != null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        try {
            configSaveService.save(boardForm, errors);
        } catch (CommonException e) {
            errors.reject("BoardConfigError", e.getMessage());
        }

        if (errors.hasErrors()){
            return "admin/board/config";
        }

        return "redirect:/admin/board"; // 게시판 목록으로 이동
    }

    private void commonProcess(Model model, String title){

        // 서브 메뉴 처리
        String subMenuCode = Menus.getSubMenuCode(request);
        subMenuCode = title.equals("게시판 수정") ? "register" : subMenuCode;
        model.addAttribute("subMenuCode", subMenuCode);

        List<MenuDetail> submenus = Menus.gets("board");
        model.addAttribute("submenus", submenus);

        model.addAttribute("pageTitle", title);
        model.addAttribute("title", title);

    }
}
