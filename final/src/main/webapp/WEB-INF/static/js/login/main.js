window.onload = () => {
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
	else if (document.getElementById('pwdTxt').value == '') {
		alert('비밀번호를 입력해주세요.');
	}
	else if (document.getElementById('pwdTxt').value != document.getElementById('confirmPwdTxt').value) {
		alert('비밀번호와 비밀번호 확인은 같아야 합니다.');
	}
	else if (document.getElementById('nicknameTxt').value == '') {
		alert('닉네임을 입력해주세요.');
	}
	else {
		return true;
	}

	return false;
}