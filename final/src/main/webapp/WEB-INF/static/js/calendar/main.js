import { mockData } from './mockData.js';
import { Spinner } from './spinner.js';
import { Calendar } from './event.js';

let renderId = 'calendar';
document.addEventListener('DOMContentLoaded', async() => {
	const cal = Calendar(renderId);
	const spr = Spinner(renderId);
	await spr.renderSpinner().delay(0);
	cal.bindData(mockData);
	cal.render();
});