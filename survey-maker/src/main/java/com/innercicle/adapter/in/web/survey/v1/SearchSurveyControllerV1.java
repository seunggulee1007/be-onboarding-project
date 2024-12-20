package com.innercicle.adapter.in.web.survey.v1;

import com.innercicle.adapter.in.web.survey.v1.response.SearchSurveyResponse;
import com.innercicle.application.port.in.v1.SearchSurveyQueryV1;
import com.innercicle.application.port.in.v1.SearchSurveyUseCaseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey")
public class SearchSurveyControllerV1 {

    private final SearchSurveyUseCaseV1 searchSurveyUseCaseV1;

    @GetMapping("/{surveyId}")
    public ResponseEntity<SearchSurveyResponse> searchSurvey(@PathVariable(name = "surveyId") Long surveyId) {
        return ResponseEntity.ok().body(SearchSurveyResponse.from(searchSurveyUseCaseV1.searchSurvey(SearchSurveyQueryV1.of(surveyId))));
    }

}
