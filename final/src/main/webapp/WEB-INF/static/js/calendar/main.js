import { Spinner } from './spinner.js';
import { Calendar } from './event.js';

document.addEventListener('DOMContentLoaded', async() => {
	const renderId = 'calendar';
	const cal = Calendar(renderId);
	const spr = Spinner(renderId);
	await spr.renderSpinner().delay(0);
	cal.bindData(datas);
	cal.render();
});
