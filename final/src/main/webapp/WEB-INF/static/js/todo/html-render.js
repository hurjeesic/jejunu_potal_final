const $result = document.querySelector('#result');

function render(data) {
	const html = data.map((todo, index) =>
		`<li data-no="${todo.no}" data-index="${index}">
    		<button class="delete">×</button>
    		<input type="checkbox" class="toggle-checked" ${todo.complete ? 'checked' : ''}/>
    		<span class="text">${todo.title}</span>
    	</li>`
	);

	$result.innerHTML = `<ul>${html.join('')}</ul>`;
}

export { render };
