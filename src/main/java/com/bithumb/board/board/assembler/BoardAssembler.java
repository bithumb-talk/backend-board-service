package com.bithumb.board.board.assembler;

import com.bithumb.board.board.api.AllBoardController;
import com.bithumb.board.board.api.BoardController;
import com.bithumb.board.board.domain.Board;
import com.bithumb.board.board.domain.BoardModel;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.domain.CommentModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BoardAssembler extends RepresentationModelAssemblerSupport<Board, BoardModel> {
    public BoardAssembler(){
        super(AllBoardController.class, BoardModel.class);
    }

    @Override
    public BoardModel toModel(Board entity) {
        BoardModel model = instantiateModel(entity);
        model.toBoardModel(entity);
        model.add(linkTo(methodOn(BoardController.class).retrieveBoard(entity.getBoardNo())).withSelfRel());
        return model;
    }
    @Override
    public CollectionModel<BoardModel> toCollectionModel(Iterable<? extends Board> entities){
        CollectionModel<BoardModel> boardModels = super.toCollectionModel(entities);
        return boardModels;
    }
}
