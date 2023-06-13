# *Protify - Postman Collection*

## Oauth2 settings in Postman

### Authorization

- Type - OAuth2.0

### Configure New Token

- Token Name - exampleTokenName
- Grant Type - Authorization Code
- Callback URL - [X] Authorize using browser
- Auth URL - http://localhost:8080/oauth2/authorize
- Access Token URL - http://localhost:8080/oauth2/token
- Client ID - swagger
- Client Secret - swagger
- Scope - openid profile

### Example users

*Standard user:*

Can only operate on his own playlists and account. He can create update and delete objects that he owns. He can't make changing operations on songs, artists and other users or other users playlists.
- Login: john123
- Password: password123

*Superuser*

Can do everything
- Login: admin
- Password: admin