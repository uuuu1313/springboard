package com.koreait.models.board;

import com.koreait.entities.BoardData;
import com.koreait.repositories.BoardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDataInfoService {

    private final BoardDataRepository boardDataRepository;

    public BoardData get(Long id) {
        BoardData boardData = boardDataRepository.findById(id).orElseThrow(BoardDataNotExistException::new);

        return null;
    }
}
