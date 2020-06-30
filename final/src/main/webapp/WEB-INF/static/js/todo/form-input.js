import render from './html-render.js';

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
		// 아이템 추가
		$.ajax({
			url: `${root}/todo/insert`,
			method: 'post',
			dataType: 'json',
			data: {
				title: text,
				time: today,
				complete: false
			},
			success: (insertedTodo) => {
				data.push({
					no: insertedTodo.no,
					title: insertedTodo.title,
					time: new Date(insertedTodo.time),
					complete: insertedTodo.complete
				});

				render(data);
			},
			error: (error) => {
				alert('서버 오류로 추가할 수 없습니다.');
			}
		});
	});
}

export default {
	init
};
