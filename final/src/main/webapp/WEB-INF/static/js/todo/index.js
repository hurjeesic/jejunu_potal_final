import render from './html-render.js';
import formInput from './form-input.js';

const $result = document.querySelector('#result');

$result.addEventListener('click', (event) => {
	const { className, id } = event.target;
	const { index, no } = event.target.parentElement.dataset;
	if (id === 'delete') {
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
	else if (id === 'confirm') {
		window.location = `${root}/todo/confirm/${no}`;
	}
	else if (className === 'toggle-checked') {
		event.preventDefault();

		$.ajax({
			url: `${root}/todo/complete/${no}`,
			method: 'put',
			dataType: 'json',
			success: (data) => {
				if (data) {
					todoList[index].complete = data.complete;
					render(todoList);
				}
			},
			error: (error) => {
				alert('서버 오류로 완료할 수 없습니다.');
			}
		});
	}

	render(todoList);
});

formInput.init(todoList, today);
render(todoList);
