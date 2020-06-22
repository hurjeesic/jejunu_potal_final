import { render } from './html-render.js';
import formInput from './form-input.js';

const $result = document.querySelector('#result');

$result.addEventListener('click', (event) => {
	const { className } = event.target;
	const { index, no } = event.target.parentElement.dataset;
	if (className === 'delete') {
		$.ajax({
			url: `${root}/todo/delete/${no}`,
			method: 'delete',
			dataType: 'json',
			success: (data) => {
				todoList.splice(index, 1);
				render(todoList);
			},
			error: (error) => {
				alert('서버 오류로 삭제할 수 없습니다.');
			}
		});
	}
	else if (className === 'toggle-checked') {
		todoList[index].complete = !todoList[index].complete;
	}
});

formInput.init(todoList, today);
render(todoList);
