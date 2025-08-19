package com.example.selling.ui.screens.Authenification

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.selling.R
import com.example.selling.ui.viewModel.AuthUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    uiState: StateFlow<AuthUiState>,
    conectMail: (String)->Unit,
    connectPassword: (String)->Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,

    ) {
    Column(
        modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo
            Image(
                painter = painterResource(R.drawable.sellinglogo),
                contentDescription = "App Logo",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 24.dp)
            )

            // Username
            /*
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Nom d'utilisateur") },
                modifier = Modifier.fillMaxWidth()
            )

             */

            Spacer(modifier = Modifier.height(12.dp))

            // Email
            OutlinedTextField(
                value = uiState.collectAsState().value.email,
                onValueChange = conectMail,
                label = { Text("Adresse email") },
                textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password
            OutlinedTextField(
                value = uiState.collectAsState().value.password,
                onValueChange = connectPassword,
                label = { Text("Mot de passe") },
                textStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))



            Spacer(modifier = Modifier.height(24.dp))

            // Create Account Button
            Button(
                onClick = { onSignInClick },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Se Connecter")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Already have an account?
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Vous n'avez  un compte ? ")
                Text(
                    text = "Cree un compte",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }
        }

        // Footer
        Text(
            text = "Created by Nathan",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        //LoginScreen({})
    }
}
