import { render } from './html-render.js';
import formInput from './form-input.js';

const $result = document.querySelector('#result');

$result.addEventListener('click', (event) => {
	const { className } = event.target;
	const { index } = event.target.parentElement.dataset;
	if (className === 'delete') {
		todoList.splice(index, 1);
	}
	else if (className === 'toggle-checked') {
		// todos[index].isDone = !todos[index].isDone;
	}

	render(todoList);
});

formInput.init(todoList, today);
render(todoList);
