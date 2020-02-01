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

import br.com.jitec.quiz.business.dto.QuestionDto;
import br.com.jitec.quiz.business.dto.QuizDto;
import br.com.jitec.quiz.business.dto.TemplateDto;
import br.com.jitec.quiz.business.mapper.ObjectMapper;
import br.com.jitec.quiz.business.service.QuizService;
import br.com.jitec.quiz.business.service.TemplateService;
import br.com.jitec.quiz.presentation.payload.QuestionRequest;
import br.com.jitec.quiz.presentation.payload.QuestionResponse;
import br.com.jitec.quiz.presentation.payload.QuizRequest;
import br.com.jitec.quiz.presentation.payload.QuizResponse;
import br.com.jitec.quiz.presentation.payload.SimpleTemplateResponse;
import br.com.jitec.quiz.presentation.payload.TemplateRequest;
import br.com.jitec.quiz.presentation.payload.TemplateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Template and Question manipulation")
@RestController
@RequestMapping(path = "/templates")
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private QuizService quizService;

	@ApiOperation(value = "Gets all templates")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return all templates") })
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<SimpleTemplateResponse> getTemplates() {

		List<TemplateDto> templates = templateService.getTemplates();
		return ObjectMapper.mapAll(templates, SimpleTemplateResponse.class);
	}

	@ApiOperation(value = "Creates a new template", code = 201)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Return created template")
	})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TemplateResponse> postTemplate(
			@ApiParam("Template information for a new template to be created") @RequestBody TemplateRequest template) {

		TemplateDto templateDto = ObjectMapper.map(template, TemplateDto.class);
		TemplateDto savedTemplate = templateService.saveTemplate(templateDto);

		TemplateResponse response = ObjectMapper.map(savedTemplate, TemplateResponse.class);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Activate the specified templateUid template", notes = "An active template can be used to create a quiz", code = 200)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return template with status changed") })
	@PostMapping(path = "/{templateUid}/activate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TemplateResponse> activateTemplate(
			@ApiParam("templateUid for the template to be activated") @PathVariable String templateUid) {

		TemplateDto template = templateService.activateTemplate(templateUid);
		TemplateResponse response = ObjectMapper.map(template, TemplateResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Inctivate the specified templateUid template", notes = "An inactive template cannot be used to create a quiz", code = 200)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return template with status changed") })
	@PostMapping(path = "/{templateUid}/inactivate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TemplateResponse> inactivateTemplate(
			@ApiParam("templateUid for the template to be inactivated") @PathVariable String templateUid) {

		TemplateDto template = templateService.inactivateTemplate(templateUid);
		TemplateResponse response = ObjectMapper.map(template, TemplateResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Gets a Template with the specified templateUid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return the Template with the specified templateUid") })
	@GetMapping(path = "/{templateUid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TemplateResponse> getTemplate(
			@ApiParam("templateUid for the template to be returned") @PathVariable String templateUid) {

		TemplateDto template = templateService.getTemplate(templateUid);
		TemplateResponse response = ObjectMapper.map(template, TemplateResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Updates a Template with the specified templateUid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return the updated template") })
	@PutMapping(path = "/{templateUid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TemplateResponse> putTemplate(
			@ApiParam("templateUid for the template to be updated") @PathVariable String templateUid,
			@ApiParam("Template information for the template to be updated") @RequestBody TemplateRequest template) {

		TemplateDto templateDto = ObjectMapper.map(template, TemplateDto.class);
		TemplateDto updatedTemplate = templateService.updateTemplate(templateUid, templateDto);
		TemplateResponse response = ObjectMapper.map(updatedTemplate, TemplateResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Deletes a Template with the specified templateUid")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Template deleted. No content to return") })
	@DeleteMapping(path = "/{templateUid}")
	public ResponseEntity<Void> deleteTemplate(
			@ApiParam("templateUid for the template to be deleted") @PathVariable String templateUid) {

		templateService.deleteTemplate(templateUid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Creates a new quiz based on the specified templateUid", code = 201)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Return created quiz") })
	@PostMapping(path = "/{templateUid}/quizzes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuizResponse> postQuiz(@PathVariable String templateUid,
			@RequestBody QuizRequest quizRequest) {

		QuizDto quizDto = ObjectMapper.map(quizRequest, QuizDto.class);
		QuizDto savedQuiz = quizService.saveQuiz(templateUid, quizDto);

		QuizResponse response = ObjectMapper.map(savedQuiz, QuizResponse.class);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Creates a new Question, that is related to specified templateUid template")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Return created question") })
	@PostMapping(path = "/{templateUid}/questions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionResponse> postQuestion(
			@ApiParam("templateUid for the template to which the question will be created") @PathVariable String templateUid,
			@ApiParam("Question information for a new question to be created") @RequestBody QuestionRequest question) {

		QuestionDto questionDto = ObjectMapper.map(question, QuestionDto.class);
		QuestionDto savedQuestion = templateService.saveQuestion(templateUid, questionDto);

		QuestionResponse response = ObjectMapper.map(savedQuestion, QuestionResponse.class);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Updates a Question with the specified questionUid, that is related to specified templateUid template")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return the updated question") })
	@PutMapping(path = "/{templateUid}/questions/{questionUid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionResponse> putQuestion(
			@ApiParam("templateUid for the template which the question will be updated") @PathVariable String templateUid,
			@ApiParam("questionUid for the question to be updated") @PathVariable String questionUid,
			@ApiParam("Question information for the question to be updated") @RequestBody QuestionRequest question) {

		QuestionDto questionDto = ObjectMapper.map(question, QuestionDto.class);
		QuestionDto updatedQuestion = templateService.updateQuestion(templateUid, questionUid, questionDto);

		QuestionResponse response = ObjectMapper.map(updatedQuestion, QuestionResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Deletes a Question with the specified questionUid, that is related to specified templateUid template")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Question deleted. No content to return") })
	@DeleteMapping(path = "/{templateUid}/questions/{questionUid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteQuestion(
			@ApiParam("templateUid for the template which the question will be deleted") @PathVariable String templateUid,
			@ApiParam("questionUid for the question to be deleted") @PathVariable String questionUid) {

		templateService.deleteQuestion(templateUid, questionUid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
