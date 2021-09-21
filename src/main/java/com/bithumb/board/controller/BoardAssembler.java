package com.bithumb.board.controller;

import com.bithumb.board.domain.Board;
import com.bithumb.board.domain.BoardModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BoardAssembler extends RepresentationModelAssemblerSupport<Board, BoardModel> {
    public BoardAssembler(){
        super(BoardController.class, BoardModel.class);
    }
    @Override
    public BoardModel toModel(Board entity) {
        BoardModel model = instantiateModel(entity);
        model.setBoardNo(entity.getBoardNo());
        model.setBoardTitle(entity.getBoardTitle());
        model.setBoardCategory(entity.getBoardCategory());
        model.setBoardContent(entity.getBoardContent());
        model.setBoardViews(entity.getBoardViews());
        model.setBoardRecommend(entity.getBoardRecommend());
        model.setBoardCreatedDate(entity.getBoardCreatedDate());
        model.setBoardModifyDate(entity.getBoardModifyDate());
        model.add(linkTo(methodOn(BoardController.class).retrieveBoard(entity.getBoardNo())).withSelfRel());

        return model;
    }
//    @Override
//    public BoardModel toModel(Board entity) {
//        return createModelWithId(entity.getBoardNo(), entity);
//    }
//    @Override
//    public CollectionModel<BoardModel> toCollectionModel(Iterable<? extends Board> entities){
//
//        return super.toCollectionModel(entities.)
//
//                .add(linkTo(methodOn(BoardController.class).hateoasCollection()).withSelfRel())
//                ;
//    }


}
