function changeImage(event) {
	let image = document.getElementById('preview');
	let target = event.target;
	let reader = new FileReader();
	reader.onload = () => {
		image.src = reader.result;
	};

	reader.readAsDataURL(target.files[0]);
}