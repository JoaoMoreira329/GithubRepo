class Controller {
    constructor(view, service) {
        this.view = view;
        this.service = service;
        this.currentQuestion = 0;

        this.view.onSubmit(this.handleFormSubmit.bind(this));
        this.loadQuestions();
    }

    async loadQuestions() {
        const questions = await this.service.loadQuestions();
        this.questions = questions;
        this.view.displayQuestion(questions[this.currentQuestion]);
    }

    handleFormSubmit(name, age) {
        this.view.hideForm();
        this.view.showQuestionContainer();
        this.view.resetQuestionContainer();
        this.view.displayQuestion(this.questions[this.currentQuestion]);
    }

    handleChoiceSelect(choiceIndex) {
        if (this.currentQuestion < this.questions.length) {
            this.currentQuestion = choiceIndex;
            this.view.resetQuestionContainer();

            if (this.currentQuestion < this.questions.length) {
                this.view.displayQuestion(this.questions[this.currentQuestion]);
            } else {    
                // Show image based on answers (implement this logic)
            }
        }
    }
}

const controller = new Controller(new View(), new Service());