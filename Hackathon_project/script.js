const form = document.getElementById('question-form');
const questionContainer = document.getElementById('question-container');
const questionElement = document.getElementById('question');
const choicesElement = document.getElementById('choices');
let currentQuestion = -1;
let selectedPoints = 0; // Initialize the selectedPoints variable
const nextButton = document.getElementById('nextButton');
const prevButton = document.getElementById('prevButton'); // Define prevButton

const loadQuestion = async () => {
    try {
        const response = await fetch('questions.json');
        const data = await response.json();
        return data.questions;
    }catch (error){
        console.error('Something went wrong with loading the questions!!!!!', error)
    }
};

const displayQuestion = (question) => {
    questionElement.textContent = `Question ${currentQuestion + 1}: ${question.question}`;
    choicesElement.innerHTML = '';

    question.choices.forEach((choice, index) => {
        const choiceDiv = document.createElement('div');
        choiceDiv.classList.add('form-check', 'mb-2');

        const input = document.createElement('input');
        input.classList.add('form-check-input');
        input.type = 'radio';
        input.name = `q${currentQuestion}`;
        input.id = `q${currentQuestion}Option${index}`;
        input.value = index;

        const label = document.createElement('label');
        label.classList.add('form-check-label');
        label.setAttribute('for', `q${currentQuestion}Option${index}`);
        label.textContent = choice.text;

        // Add onclick attribute directly to the label
        label.setAttribute('onclick', `highlightSelected(this, ${index})`);

        choiceDiv.appendChild(input);
        choiceDiv.appendChild(label);
        choicesElement.appendChild(choiceDiv);
    });
};



const nextQuestion = () => {
    currentQuestion++;
    if (currentQuestion < questions.length) {
        displayQuestion(questions[currentQuestion]);
    } else {
       questionContainer.style.display = 'none'; 
    }
};

let questions;

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const age = document.getElementById('age').value;

    if (name && age) {
        questions = await loadQuestion();
        questionContainer.classList.remove('hidden');
        form.classList.add('hidden');
        nextQuestion();
    }
});

nextButton.addEventListener('click', () => {
    const selectedOption = document.querySelector(`input[name="q${currentQuestion}"]:checked`);


    if (selectedOption) {

        const selectedChoice = questions[currentQuestion].choices[selectedOption.value];
        selectedPoints += selectedChoice.points;

       if (currentQuestion === questions.length - 1) {
            questionContainer.style.display = 'none';
            var div = document.getElementById("initialText");
            const resultTextElement = document.getElementById('final-container');

            resultTextElement.classList.remove('hidden');
            div.style.display = "none";
            displayImageBasedOnPoints(selectedPoints);
            showResultView();
        } else {
            nextQuestion();
        }
    } else {
        console.error('Something went wrong with loading the next question!!!!!', error)
    }
});

prevButton.addEventListener('click', () => {
    if (currentQuestion > 0) {
        currentQuestion--;
        displayQuestion(questions[currentQuestion]);
    }
});

function displayImageBasedOnPoints(points) {
    const resultImageElement = document.getElementById('resultImage');
    
    if(points >= 10 && points <= 11){
        resultImageElement.src = "images/strong.jpg";
    } else if(points > 11 && points <= 13){
        resultImageElement.src = "images/strong2.jpg";
    } else if(points > 13 && points <= 15){
        resultImageElement.src = "images/strong3.jpg";
    } else if(points > 15 && points <= 17){
        resultImageElement.src = "images/romantic.jpg";
    } else if(points > 17 && points <= 19){
        resultImageElement.src = "images/romantic2.jpg";
    } else if(points > 19 && points <= 21){
        resultImageElement.src = "images/romantic3.jpg";
    } else if(points > 21 && points <= 22){
        resultImageElement.src = "images/adventurous.jpg";
    } else if(points > 22 && points <= 24){
        resultImageElement.src = "images/adventurous2.jpg";
    } else if(points > 24 && points <= 25){
        resultImageElement.src = "images/adventurous3.jpg";
    } else if(points > 25 && points <= 27){
        resultImageElement.src = "images/dancer.jpg";
    } else if(points > 27 && points <= 29){
        resultImageElement.src = "images/dancer2.jpg";
    } else if(points > 29 && points <= 31){
        resultImageElement.src = "images/dancer3.jpg";
    } else if (points > 31 && points <= 33){
        resultImageElement.src = "images/depressed.jpg";
    } else if (points > 33 && points <= 35){
        resultImageElement.src = "images/depressed4.jpg";
    } else if (points > 35 && points <= 37){
        resultImageElement.src = "images/depressed2.jpg";
    } else if (points > 37 && points <= 40){
        resultImageElement.src = "images/depressed3.jpg";
    } 
 resultImageElement.classList.remove('hidden');
}

function showResultView() {
    const resultView = document.getElementById('result-view');
    resultView.classList.remove('hidden');
}

document.fonts.ready.then(function() {
    // Apply font styles
    document.body.style.fontFamily = "'Orbitron', sans-serif";
});

// Get all choice buttons
const choiceButtons = document.querySelectorAll('.choice-btn');

choiceButtons.forEach(button => {
    button.addEventListener('click', function() {
        // Remove the selected class from the previously selected button
        const previouslySelectedButton = choicesElement.querySelector('.choice-btn.selected');
        if (previouslySelectedButton) {
            previouslySelectedButton.classList.remove('.selected');
        }

        this.classList.add('.selected');
    });
});

function highlightSelected(label, index) {
    const selectedLabels = choicesElement.querySelectorAll('.form-check-label');
    selectedLabels.forEach(selectedLabel => {
        selectedLabel.classList.remove('selected');
    });

    label.classList.add('selected');

    // Manually select the corresponding input
    const input = label.previousElementSibling;
    input.checked = true;
}

// Get the startButton element
const startButton = document.getElementById('startButton');
const restartSound = document.getElementById('restartSound');
const loadingBar = startButton.querySelector('.loading-bar');

startButton.addEventListener('mouseover', () => {
    // Play the sound clip
    restartSound.play();
});
startButton.addEventListener('mouseout', () => {
    // Pause and reset the audio to the beginning
    restartSound.pause();
    restartSound.currentTime = 0;
});
// Add event listener to the start button
startButton.addEventListener('click', () => {
    // Reload the page to start over
    location.reload();
});



