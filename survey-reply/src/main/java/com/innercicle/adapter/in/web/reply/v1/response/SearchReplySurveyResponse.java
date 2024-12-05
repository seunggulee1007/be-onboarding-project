package com.innercicle.adapter.in.web.reply.v1.response;

import com.innercicle.domain.InputType;
import com.innercicle.domain.ReplySurvey;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchReplySurveyResponse {

    private Long id;
    private Long surveyId;
    private String name;
    private String replierEmail;
    private String description;
    private List<SearchReplySurveyItemResponse> items;

    public static SearchReplySurveyResponse from(ReplySurvey replySurvey) {
        return SearchReplySurveyResponse.builder()
            .id(replySurvey.id())
            .surveyId(replySurvey.surveyId())
            .name(replySurvey.name())
            .replierEmail(replySurvey.replierEmail())
            .description(replySurvey.description())
            .items(replySurvey.items().stream()
                       .map(item -> SearchReplySurveyResponse.SearchReplySurveyItemResponse.builder()
                           .id(item.id())
                           .surveyItemId(item.surveyItemId())
                           .item(item.item())
                           .description(item.description())
                           .inputType(item.inputType())
                           .required(item.required())
                           .replyText(item.replyText())
                           .options(item.options().stream()
                                        .map(option -> SearchReplySurveyResponse.SearchReplySurveyItemResponse.ItemOptionResponse.builder()
                                            .option(option.option())
                                            .checked(option.checked())
                                            .build())
                                        .toList())
                           .build())
                       .toList())
            .build();
    }

    @Getter
    @Builder
    private static class SearchReplySurveyItemResponse {

        private Long id;                // 식별자
        private Long surveyItemId;
        private String item;            // 항목
        private String description;     // 설명
        private InputType inputType;    // 입력 형태
        private boolean required;       // 필수 여부
        private String replyText;        // 응답 내용
        private List<SearchReplySurveyItemResponse.ItemOptionResponse> options;    // 선택지 목록

        @Getter
        @Builder
        private static class ItemOptionResponse {

            private String option;
            private boolean checked;

        }

    }
    
}