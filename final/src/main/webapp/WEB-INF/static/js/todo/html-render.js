const $result = document.querySelector('#result');

export default function render(data) {
	const html = data.map((todo, index) =>
		`<li data-no="${todo.no}" data-index="${index}">
    		<button class="delete" id="delete">×</button>
    		<button class="delete" id="confirm">자세히 보기</button>
    		<input type="checkbox" class="toggle-checked" ${todo.complete ? 'checked' : ''}/>
    		<span class="text">${todo.title}</span>
    	</li>`
	);

	$result.innerHTML = `<ul>${html.join('')}</ul>`;
}