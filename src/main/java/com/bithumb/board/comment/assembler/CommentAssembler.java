package com.bithumb.board.comment.assembler;


import com.bithumb.board.board.api.BoardController;
import com.bithumb.board.comment.api.CommentController;
import com.bithumb.board.comment.domain.Comment;
import com.bithumb.board.comment.domain.CommentModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentAssembler extends RepresentationModelAssemblerSupport<Comment, CommentModel> {
    public CommentAssembler(){
        super(CommentController.class, CommentModel.class);
    }

    @Override
    public CommentModel toModel(Comment entity) {
        CommentModel model = instantiateModel(entity);
        model.changeModel(entity);
        model.add(linkTo(methodOn(BoardController.class).retrieveBoard(entity.getBoard().getBoardNo())).withRel("board"));
        return model;
    }
}
