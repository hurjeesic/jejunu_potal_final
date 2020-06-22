import { render } from './html-render.js';

const $inputForm = document.querySelector('#input-form');
const $input = document.querySelector('#input');

function init(data, today) {
	$inputForm.addEventListener('submit', (event) => {
		event.preventDefault();

		const text = $input.value;
		if (!text) {
			return;
		}

		$input.value = '';
		data.push({
			title: todo.title,
			time: today,
			complete: false
		});

		render(data);
	});
}

export default {
	init
};
