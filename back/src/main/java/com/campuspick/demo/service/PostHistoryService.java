package com.campuspick.demo.service;

import com.campuspick.demo.domain.entity.PostHistory;
import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.domain.repository.PostHistoryRepository;
import com.campuspick.demo.domain.repository.UserRepository;
import com.campuspick.demo.dto.PostHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostHistoryService {
    private final PostHistoryRepository postHistoryRepository;
    private final UserRepository userRepository;

    // 임시저장글 리스트 가져오기
    @Transactional
    public List<PostHistory> readPostHistoryHist(){
        // 로그인 한 User 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityExistsException());

        return postHistoryRepository.findAllByUserId(user.getId());
    }

    // 임시저장글 저장
    @Transactional
    public PostHistory saves(PostHistoryDto.PostHistoryCreateRequestDto request){
        // tagList to String
        String tagList = "";
        if(request.getTagList() != null){
            int tagLength = request.getTagList().size();
            for (int i = 0; i<tagLength; i++){
                tagList += request.getTagList().get(i).getName() + (i == tagLength-1 ? "" : ",");
            }
            System.out.println(tagList);
        }

        // 로그인 한 User 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityExistsException());

        // url 없는 postHistory 등록
        PostHistory tempHistory = PostHistory.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .tagList(tagList)
                .user(user)
                .build();

        // Url을 id로 설정해주기 위해서 update
        PostHistory postHistory = postHistoryRepository.save(tempHistory);
        postHistory.setUrl(postHistory.getId().toString());

        return postHistoryRepository.save(postHistory);
    }

    // 임시저장글 detail 가져오기
    public PostHistory write(UUID id){
        return postHistoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("임시저장글이 존재하지 않습니다."));
    }

    // 임시저장글 업데이트
//    @Transactional
//    public PostHistory updatePostHistory(PostHistoryDto.PostHistoryUpdateReqeustDto request){
//
//        return ;
//    }
}
