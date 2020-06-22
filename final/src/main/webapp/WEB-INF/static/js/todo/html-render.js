const $result = document.querySelector('#result');

function render(data) {
	const html = data.map((todo, index) =>
		`<li data-index="${index}">
			<input type="hidden" value="${todo.no}">
    		<button class="delete">×</button>
    		<input type="checkbox" class="toggle-checked" ${todo.complete  ? 'checked' : ''}/>
    		<span class="text">${todo.title}</span>
    	</li>`
	);

	$result.innerHTML = `<ul>${html.join('')}</ul>`;
}

export { render };
