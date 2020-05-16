# Quiz API

[![Build Status](https://travis-ci.com/itiburski/quiz-boot.svg?branch=master)](https://travis-ci.com/itiburski/quiz-boot) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This is an API for managing Quiz and Answers.

The API allows users to create quiz Templates with some predefined questions.

When a Template is ready it can be turned Active, so the template can be used to create a Quiz based on it (that is, using the Template's questions). A Quiz should also specify a date range when the Quiz will be availabe to gather Answers.

For ended Quizzes, there is a summary containing the number of answers for each choice for each quiz's question.


## How to use it

```sh
$ git clone https://github.com/itiburski/quiz-boot.git
```

+ Open project in your favorite editor and change application.properties file to point to your PostgreSQL database
+ Build Spring project 
+ Open http://localhost:8080/quiz/swagger-ui.html to see endpoints.
