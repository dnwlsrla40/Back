package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.Comment;
import com.campuspick.demo.domain.entity.Post;
import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.domain.repository.CommentRepository;
import com.campuspick.demo.domain.repository.PostRepository;
import com.campuspick.demo.domain.repository.UserRepository;
import com.campuspick.demo.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Comment> getComments(UUID postId){
        return commentRepository.findByPostIdAndIsRootTrue(postId);
    }

    @Transactional
    public Comment saveComment(CommentRequestDto requestDto){
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(IllegalArgumentException::new);
        Comment comment;
        if(requestDto.getReplyTo()!=null){
            comment = commentRepository.save(Comment.builder().text(requestDto.getText()).user(user).post(post).isRoot(false).build());

            Comment replyTo = commentRepository.findById(requestDto.getReplyTo()).orElseThrow(IllegalArgumentException::new);
            replyTo.getReComment().add(comment);
        } else{
            comment = commentRepository.save(Comment.builder().text(requestDto.getText()).user(user).post(post).isRoot(true).build());
        }

        return comment;
    }

    @Transactional
    public Comment updateComment(UUID commentId, String text){
        Comment target = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        target.update(text);

        return target;
    }

}
