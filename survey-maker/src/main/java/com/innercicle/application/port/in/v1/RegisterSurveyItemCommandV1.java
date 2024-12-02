package com.innercicle.application.port.in.v1;

import com.innercicle.advice.exceptions.RequiredFieldException;
import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.SurveyItem;
import com.innercicle.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

import static com.innercicle.domain.v1.InputType.MULTI_SELECT;
import static com.innercicle.domain.v1.InputType.SINGLE_SELECT;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterSurveyItemCommandV1 extends SelfValidating<RegisterSurveyItemCommandV1> {

    /**
     * 설문 항목 명
     */
    @NotNull(message = "설문 항목 명은 필수 입니다.")
    private String item;
    /**
     * 설문 항목 설명
     */
    @NotNull(message = "설문 항목 설명은 필수 입니다.")
    private String description;
    /**
     * 설문 항목 타입
     */
    @NotNull(message = "설문 항목 타입은 필수 입니다.")
    private InputType type;

    /**
     * 필수 여부
     */
    private boolean required;

    /**
     * 설문 항목 선택지 목록
     */
    @NotNull(message = "설문 항목 선택지 목록은 필수 입니다.")
    private List<String> options;

    @Override
    public void validateSelf() {
        super.validateSelf();
        if (type == SINGLE_SELECT || type == MULTI_SELECT) {
            if (options == null || options.isEmpty()) {
                throw new RequiredFieldException(String.format("%s 일 경우 설문 항목 선택지 목록은 비어있을 수 없습니다.", this.type.getType()));
            }
            if (options.size() < 2) {
                throw new RequiredFieldException(String.format("%s 일 경우 선택지는 2개 이상 입력해 주세요.", this.type.getType()));
            }
        }

    }

    @Builder(builderClassName = "RegisterSurveyItemCommandV1Builder", builderMethodName = "buildInternal")
    public static RegisterSurveyItemCommandV1 create(String item,
                                                     String description,
                                                     InputType type,
                                                     boolean required,
                                                     List<String> options) {
        return new RegisterSurveyItemCommandV1(item, description, type, required, options);
    }

    /**
     * builder 호출 이후 validation 체크를 위한 build 메소드
     */
    public static class RegisterSurveyItemCommandV1Builder {

        public RegisterSurveyItemCommandV1 build() {
            RegisterSurveyItemCommandV1 registerSurveyCommandV1 =
                create(item, description, type, required, options);
            registerSurveyCommandV1.validateSelf();
            return registerSurveyCommandV1;
        }

    }

    public SurveyItem mapToDomain() {
        return SurveyItem.builder()
            .item(item)
            .description(description)
            .inputType(type)
            .required(required)
            .options(options)
            .build();
    }

}
