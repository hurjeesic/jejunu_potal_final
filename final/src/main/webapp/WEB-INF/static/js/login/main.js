let isId = false, isNickname = false;
window.onload = () => {
	document.getElementById('confirmIdBtn').addEventListener('click', (e) => confirmId());
	document.getElementById('confirmNicknameBtn').addEventListener('click', (e) => confirmNickname());
	document.getElementById('loginBtn').addEventListener('click', (e) => login());
	document.querySelector('form[name=login] input[name=password]').addEventListener('keydown', (e) => {
		if (e.keyCode == 13) {
			login();
		}
	});
	document.getElementById('registerBtn').addEventListener('click', (e) => {
		if (valid()) {
			document.register.submit();
		}
	});

	$('.login-info-box').fadeOut();
	$('.login-show').addClass('show-log-panel');

	$('.login-reg-panel input[type="radio"]').on('change', function () {
		if ($('#log-login-show').is(':checked')) {
			document.title = 'Sign Up';
			$('.register-info-box').fadeOut();
			$('.login-info-box').fadeIn();

			$('.white-panel').addClass('right-log');
			$('.register-show').addClass('show-log-panel');
			$('.login-show').removeClass('show-log-panel');

		}
		else if ($('#log-reg-show').is(':checked')) {
			document.title = 'Login';
			$('.register-info-box').fadeIn();
			$('.login-info-box').fadeOut();

			$('.white-panel').removeClass('right-log');

			$('.login-show').addClass('show-log-panel');
			$('.register-show').removeClass('show-log-panel');
		}
	});
};

function confirmId() {
	const idTxt = document.getElementById('idTxt');
	const confirmIdBtn = document.getElementById('confirmIdBtn');
	if (idTxt.value == '') {
		alert('아이디를 입력해주세요.');
	}
	else {
		$.ajax({
			url: `${root}/user/id/confirm`,
			method: 'get',
			dataType: 'json',
			data: { id: idTxt.value },
			success: (data) => {
				isId = data;
				if (!data) {
					alert('중복확인이 완료되었습니다.');
					idTxt.disabled = true;
					confirmIdBtn.disabled = true;
				}
				else {
					alert('존재하는 아이디입니다.');
				}
			},
			error: (error) => {
				alert('서버 오류로 중복 확인을 할 수 없습니다.');
			}
		});
	}
}

function confirmNickname() {
	const nicknameTxt = document.getElementById('nicknameTxt');
	const confirmNicknameBtn = document.getElementById('confirmNicknameBtn');
	if (document.getElementById('nicknameTxt').value == '') {
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
				if (!data) {
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
}

function login() {
	if (!document.querySelector('form[name=login] input[name=id]').value) {
		alert('아이디를 입력해주세요.');
	}
	else if (!document.querySelector('form[name=login] input[name=password]').value) {
		alert('비밀번호를 입력해주세요.');
	}
	else {
		document.login.submit();
	}
}

function valid() {
	if (document.getElementById('idTxt').value == '') {
		alert('아이디를 입력해주세요.');
	}
	else if (!isId) {
		alert('아이디 중복 검사를 해주세요.');
	}
	else if (document.getElementById('pwdTxt').value == '') {
		alert('비밀번호를 입력해주세요.');
	}
	else if (document.getElementById('pwdTxt').value != document.getElementById('confirmPwdTxt').value) {
		alert('비밀번호와 비밀번호 확인은 같아야 합니다.');
	}
	else if (document.getElementById('nicknameTxt').value == '') {
		alert('닉네임을 입력해주세요.');
	}
	else if (!isNickname) {
		alert('닉네임 중복 검사를 해주세요.');
	}
	else {
		return true;
	}

	return false;
}