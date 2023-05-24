package com.koreait.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardData is a Querydsl query type for BoardData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardData extends EntityPathBase<BoardData> {

    private static final long serialVersionUID = 15931753L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardData boardData = new QBoardData("boardData");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBoard board;

    public final StringPath category = createString("category");

    public final NumberPath<Integer> commentCnt = createNumber("commentCnt", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath gid = createString("gid");

    public final StringPath guestPw = createString("guestPw");

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ip = createString("ip");

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath poster = createString("poster");

    public final StringPath subject = createString("subject");

    public final StringPath ua = createString("ua");

    public QBoardData(String variable) {
        this(BoardData.class, forVariable(variable), INITS);
    }

    public QBoardData(Path<? extends BoardData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardData(PathMetadata metadata, PathInits inits) {
        this(BoardData.class, metadata, inits);
    }

    public QBoardData(Class<? extends BoardData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

