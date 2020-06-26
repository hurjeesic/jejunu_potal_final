window.onload = () => {
	let isNickname = false;
	const passwordTxt = document.getElementById("passwordTxt");
	const nicknameTxt = document.getElementById("nicknameTxt");
	const confirmNicknameBtn = document.getElementById('confirmNicknameBtn');
	confirmNicknameBtn.addEventListener('click', (e) => {
		if (nicknameTxt.value == '') {
			alert('닉네임을 입력해주세요.');
		}
		else {
			$.ajax({
				url: `${root}/user/nickname/confirm`,
				method: 'get',
				dataType: 'json',
				data: { nickname: nicknameTxt.value },
				success: (data) => {
					isNickname = data;
					if (data) {
						alert('중복확인이 완료되었습니다.');
						nicknameTxt.disabled = true;
						confirmNicknameBtn.disabled = true;
					}
					else {
						alert('존재하는 별명입니다.');
					}
				},
				error: (error) => {
					alert('서버 오류로 중복 확인을 할 수 없습니다.');
				}
			});
		}
	});

	document.getElementById("updateBtn").addEventListener('click', (e) => {
		if (passwordTxt.value == '') {
			alert('변경할 비밀번호를 입력해주세요.');
		}
		else if (nicknameTxt.value == '') {
			alert('변경할 별명을 입력해주세요.');
		}
		else if (!isNickname) {
			alert('별명 중복확인을 해주세요.');
		}
		else {
			nicknameTxt.disabled = false;
			document.updatedForm.submit();
		}
	});

	document.getElementById("withdrawalBtn").addEventListener('click', (e) => {
		const password = window.prompt('삭제를 위해 비밀번호를 입력해주세요.');
		window.location = `${root}/user/delete?password=${password}`;
	});
}