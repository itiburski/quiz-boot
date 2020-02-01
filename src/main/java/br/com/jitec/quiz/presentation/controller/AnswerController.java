package br.com.jitec.quiz.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jitec.quiz.business.dto.AnswerDto;
import br.com.jitec.quiz.business.mapper.ObjectMapper;
import br.com.jitec.quiz.business.service.AnswerService;
import br.com.jitec.quiz.presentation.payload.AnswerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Answer submit")
@RestController
@RequestMapping("/quizzes/{quizUid}")
public class AnswerController {

	@Autowired
	private AnswerService answerService;

	@ApiOperation(value = "Submit an answer for the specified quizUid", notes = "The answer for all questions should be sent together")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Answer registered. No content to return") })
	@PostMapping(path = "/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> postAnswers(@PathVariable String quizUid, @RequestBody AnswerRequest answerRequest) {
		
		AnswerDto answerDto = ObjectMapper.map(answerRequest, AnswerDto.class);
		answerService.saveAnswer(quizUid, answerDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
