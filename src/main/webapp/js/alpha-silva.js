
function statusChangeCallback(response) {
    if (response.status === 'connected') {
        console.log('connected');
        findUserDatail();
    } else if (response.status === 'not_authorized') {
        console.log('not_authorized');
    } else {
        FB.login(function (response) {
            statusChangeCallback(response);
        }, {
            scope: 'publish_actions,email',
            return_scopes: true
        });
    }
}

function checkLoginState() {
    console.log('checkLoginState');
    FB.getLoginStatus(function (response) {
        statusChangeCallback(response);
    });
}

window.fbAsyncInit = function () {
    FB.init({
        appId: '1750234818552649',
        xfbml: true,
        cookie: true,
        status: true,
        version: 'v2.6'
    });
    
};
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id))
        return;
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/pt_BR/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function findUserDatail() {
    FB.api('/me?fields=name,email,birthday,gender', function (response) {
        console.log('name: ' + response.name + ' .');
        console.log('id: ' + response.id + ' .');
        console.log('email: ' + response.email + ' .');
        console.log('birthday :' + response.birthday + ' .');
        console.log('gender :' + response.gender + ' .');
        if (response.id !== null) {
            response.picture = 'http://graph.facebook.com/' + response.id + '/picture?type=large';
            //$('#foto').attr('src', response.picture);
            $('#emailId').html(function () {
                return "<i class='fa fa-envelope fa-fw w3-margin-right w3-text-theme'></i>" + response.email;
            });
            $('#nomeId').text(response.name);
        }
    });
}