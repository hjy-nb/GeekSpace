package com.yao.geek.comment.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.comment.model.dto.CommentDeleteDto;
import com.yao.geek.comment.model.dto.CommentLikeDto;
import com.yao.geek.comment.model.dto.CommentPublishDto;
import com.yao.geek.comment.model.dto.CommentReplyDto;
import com.yao.geek.comment.model.query.Query;
import com.yao.geek.comment.model.result.Result;
import com.yao.geek.comment.model.vo.CommentListVo;
import com.yao.geek.comment.model.vo.CommentVo;
import com.yao.geek.comment.service.CommentService;
import com.yao.geek.comment.service.like.CommentLikeService;
import com.yao.geek.common.constant.NumConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/geek/comment")
@Validated
public class CommentController {
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    public CommentController(CommentService commentService, CommentLikeService commentLikeService) {
        this.commentService = commentService;
        this.commentLikeService = commentLikeService;
    }

    //发表评论
    @PostMapping("/publish")
    public Result<Long> publishComment(@RequestBody @Valid CommentPublishDto commentPublishDto, @RequestHeader(NumConstant.T_ID) @NotNull Long userId){
        return Result.ok(commentService.publishComment(commentPublishDto, userId));
    }

    //回复评论
    @PostMapping("/reply")
    public Result<Long> replyComment(@RequestBody @Valid CommentReplyDto commentReplyDto, @RequestHeader(NumConstant.T_ID) @NotNull Long userId){
        return Result.ok(commentService.replyComment(commentReplyDto, userId));
    }

    //删除评论
    @DeleteMapping("/delete")
    public Result<Void> deleteComment(@RequestBody @Valid CommentDeleteDto commentDeleteDto){
        commentService.deleteComment(commentDeleteDto);

        return Result.ok(null);
    }

    @DeleteMapping("/deleteSelf")
    public Result<Void> deleteSelfComment(@RequestBody @Valid CommentDeleteDto commentDeleteDto, @RequestHeader(NumConstant.T_ID) @NotNull Long userId){
        commentService.deleteSelfComment(commentDeleteDto, userId);

        return Result.ok(null);
    }

    //评论一般不能改

    //查看评论
    @GetMapping("/view")
    public Result<CommentVo> viewComment(@RequestParam("commentId") @NotNull Long commentId){
        return Result.ok(commentService.viewComment(commentId));
    }

    //点赞
    @PatchMapping("/like")
    public Result<Long> likeComment(@RequestBody @Valid CommentLikeDto commentLikeDto){
        return Result.ok(commentLikeService.likeComment(commentLikeDto));
    }

    //查看点赞列表
    @GetMapping("/getLikeList")
    public Result<Page<Long>> getLikeList(@ModelAttribute @Valid Query query){
        return Result.ok(commentLikeService.getLikeList(query));
    }

    //查看点赞数
    @GetMapping("/getLikeCount")
    public Result<Integer> getLikeCount(@RequestParam("commentId") @NotNull Long commentId){
        return Result.ok(commentService.getLikeCount(commentId));
    }

    //查看评论状态
    @GetMapping("/getStatus")
    public Result<Integer> getStatus(@RequestParam("commentId") @NotNull Long commentId){
        return Result.ok(commentService.getStatus(commentId));
    }

    //获取文章所有评论(第一级评论)
    @GetMapping("/getArticleComments")
    public Result<Page<CommentListVo>> getArticleComments(@ModelAttribute @Valid Query query){
        return Result.ok(commentService.getArticleComments(query));
    }

    //获取评论所有回复
    @GetMapping("/getCommentReplies")
    public Result<Page<CommentListVo>> getCommentReplies(@ModelAttribute @Valid Query query){
        return Result.ok(commentService.getCommentReplies(query));
    }

    //获取文章热门评论
    @GetMapping("/getArticleHotComments")
    public Result<Page<CommentListVo>> getArticleHotComments(@ModelAttribute @Valid Query query){
        return Result.ok(commentService.getArticleHotComments(query));
    }
}
