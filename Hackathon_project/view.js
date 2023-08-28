class View {
    constructor() {
        this.form = document.getElementById('question-form');
        this.questionContainer = document.getElementById('question-container');
        this.questionElement = document.getElementById('question');
        this.choicesElement = document.getElementById('choices');
        this.nameInput = document.getElementById('name');
        this.ageInput = document.getElementById('age');

        this.form.addEventListener('submit', this.handleFormSubmit.bind(this));
        this.choicesElement.addEventListener('click', this.handleChoiceClick.bind(this));
    }

    handleFormSubmit(event) {
        event.preventDefault();
        const name = this.nameInput.value;
        const age = this.ageInput.value;

        if (name && age) {
            controller.handleFormSubmit(name, age);
        }
    }

    handleChoiceClick(event) {
        if (event.target.tagName === 'LI') {
            const choiceIndex = [...this.choicesElement.children].indexOf(event.target);
            controller.handleChoiceSelect(choiceIndex);
        }
    }

    displayQuestion(question) {
        this.questionElement.textContent = question.question;
        question.choices.forEach((choice, index) => {
            const choiceItem = document.createElement('li');
            choiceItem.textContent = choice;
            this.choicesElement.appendChild(choiceItem);
        });
    }

    hideForm() {
        this.form.style.display = 'none';
    }

    showQuestionContainer() {
        this.questionContainer.style.display = 'block';
    }

    resetQuestionContainer() {
        this.questionElement.textContent = '';
        this.choicesElement.innerHTML = '';
    }
}
