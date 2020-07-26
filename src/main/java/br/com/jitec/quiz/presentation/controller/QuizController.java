package br.com.jitec.quiz.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jitec.quiz.business.dto.QuizCompleteDto;
import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.dto.QuizSummaryDto;
import br.com.jitec.quiz.business.mapper.ObjectMapper;
import br.com.jitec.quiz.business.service.QuizService;
import br.com.jitec.quiz.presentation.payload.QuizCompleteResponse;
import br.com.jitec.quiz.presentation.payload.QuizRequest;
import br.com.jitec.quiz.presentation.payload.QuizResponse;
import br.com.jitec.quiz.presentation.payload.QuizSummaryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Quiz manipulation")
@RestController
@RequestMapping("/quizzes")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@ApiOperation(value = "Gets all quizzes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return all quizzes") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<QuizResponse> getQuizzes() {
		List<QuizDto> quizzes = quizService.getQuizzes();

		return ObjectMapper.mapAll(quizzes, QuizResponse.class);
	}

	@ApiOperation(value = "Get active quizzes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return active quizzes") })
	@GetMapping(path = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<QuizResponse> getActiveQuizzes() {
		List<QuizDto> quizzes = quizService.getActiveQuizzes();

		return ObjectMapper.mapAll(quizzes, QuizResponse.class);
	}

	@ApiOperation(value = "Gets a Quiz with the specified quizUid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return the Quiz with the specified quizUid") })
	@GetMapping(path = "/{quizUid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuizResponse> getQuiz(@PathVariable String quizUid) {

		QuizDto quizDto = quizService.getQuiz(quizUid);

		QuizResponse response = ObjectMapper.map(quizDto, QuizResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Gets a Quiz with the specified quizUid, including the questions and the possible choices")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return the Quiz with the specified quizUid") })
	@GetMapping(path = "/{quizUid}/complete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuizCompleteResponse> getQuizComplete(@PathVariable String quizUid) {

		QuizCompleteDto quizDto = quizService.getQuizComplete(quizUid);

		QuizCompleteResponse response = ObjectMapper.map(quizDto, QuizCompleteResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Updates a Quiz with the specified quizUid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return the updated quiz") })
	@PutMapping(path = "/{quizUid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuizResponse> putQuiz(@PathVariable String quizUid, @RequestBody QuizRequest quizRequest) {

		QuizDto quizDto = ObjectMapper.map(quizRequest, QuizDto.class);
		QuizDto updatedQuiz = quizService.updateQuiz(quizUid, quizDto);

		QuizResponse response = ObjectMapper.map(updatedQuiz, QuizResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Deletes a Quiz with the specified quizUid")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Quiz deleted. No content to return") })
	@DeleteMapping(path = "/{quizUid}")
	public ResponseEntity<Void> deleteQuiz(@PathVariable String quizUid) {

		quizService.deleteQuiz(quizUid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Starts the specified quizUid quiz, alowing it to receive answers", code = 200)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return quiz with status changed") })
	@PostMapping(path = "/{quizUid}/start", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuizResponse> startQuiz(@PathVariable String quizUid) {

		QuizDto updatedQuiz = quizService.startQuiz(quizUid);
		QuizResponse response = ObjectMapper.map(updatedQuiz, QuizResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Ends the specified quizUid quiz, from this moment it does not receive answers anymore", code = 200)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return quiz with status changed") })
	@PostMapping(path = "/{quizUid}/end", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuizResponse> endQuiz(@PathVariable String quizUid) {

		QuizDto updatedQuiz = quizService.endQuiz(quizUid);
		QuizResponse response = ObjectMapper.map(updatedQuiz, QuizResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Produces a summary containing the number of answers for each choice for each quiz's question", code = 200)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Return the summarized choices quantity for each quiz's question") })
	@GetMapping(path = "/{quizUid}/summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuizSummaryResponse> getSummary(@PathVariable String quizUid) {

		QuizSummaryDto quizSummary = quizService.getSummary(quizUid);
		QuizSummaryResponse response = ObjectMapper.map(quizSummary, QuizSummaryResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
