package com.RageRacoon.learm_x_coffee.presentation.screens.edit_profile.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.navigation.NavHostController
import com.RageRacoon.learm_x_coffee.R
import com.RageRacoon.learm_x_coffee.presentation.components.MyTextField
import androidx.hilt.navigation.compose.hiltViewModel
import com.RageRacoon.learm_x_coffee.presentation.components.MyDialog
import com.RageRacoon.learm_x_coffee.presentation.components.MyRoundImage
import com.RageRacoon.learm_x_coffee.presentation.navegation.AppScreen
import com.RageRacoon.learm_x_coffee.presentation.screens.edit_profile.EditProfileViewModel

@Composable
fun EditProfileContent(navController: NavHostController, viewModel: EditProfileViewModel = hiltViewModel()){

    val state = viewModel.state //States, de esta screen
    viewModel.resultingActivityHandler.handle()

    //Contexto de la aplicacion
    val contexto = LocalContext.current

    //Estado del mensaje del dialogo
    var stadoDialog = remember {
        mutableStateOf(false)
    }

    //Selección de cámara o galería para la selección de foto de perfil.
    MyDialog(
        estado = stadoDialog,
        fun01 = { viewModel.getImg() },
        accionFuncion01 = "Galería",
        fun02 = { viewModel.takeAPicture() },
        accionFuncion02 = "Cámara"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(){
            //Imagen de fondo con efecto borroso.
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(192.dp)
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp
                    ),
                painter = painterResource(id = R.drawable.banner_perfil01),
                contentDescription = "Banner imagen",
                contentScale = ContentScale.Crop,
                alpha = 0.75F
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 90.dp)
            ) {
                //Foto de perfil si el usuario ha añadido una, y nombre de usuario.
                if (viewModel.imgUri != "") {
                Box(modifier = Modifier.fillMaxWidth()){
                    AsyncImage(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(192.dp)
                            .clip(CircleShape)
                            .border(
                                BorderStroke(2.dp, MaterialTheme.colors.primary),
                                CircleShape
                            )
                            .clickable { stadoDialog.value = true },
                        model = viewModel.imgUri,
                        contentDescription = "User image",
                        contentScale = ContentScale.Crop
                    )
                    IconButton(onClick = {
                        viewModel.imgUri
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            modifier = Modifier.offset(x = 245.dp, y = 0.dp).clickable { stadoDialog.value = true },
                            contentDescription = "Icono edición de imagen",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }
                    Spacer(modifier = Modifier.height(20.dp))
                    MyTextField(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .size(width = 325.dp, height = 65.dp),
                        texto =  state.username,
                        onValueChange = { viewModel.userNameImput(it) },
                        label = "Nombre de usuario",
                        icon = Icons.Default.Person,
                    )
                }
                else {
                    //Imagen de mapache por defecto y nombre de usuario.
                    Spacer(modifier = Modifier.height(18.dp))

                    Box(modifier = Modifier.fillMaxWidth()){

                        MyRoundImage(
                            R.drawable.sprite_racoon,
                            modifier = Modifier.align(Alignment.Center)
                                               .clickable { stadoDialog.value = true }
                        )

                        IconButton(onClick = {
                            viewModel.imgUri
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                modifier = Modifier.offset(x = 230.dp, y = 0.dp).clickable { stadoDialog.value = true },
                                contentDescription = "Icono edición de imagen",
                                tint = MaterialTheme.colors.secondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    MyTextField(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .size(width = 325.dp, height = 65.dp),
                        texto =  state.username,
                        onValueChange = { viewModel.userNameImput(it) },
                        label = "Nombre de usuario",
                        icon = Icons.Default.Person,
                    )
                }
                //Descripción que el usuario desee añadir.
                Spacer(modifier = Modifier.height(20.dp))
                MyTextField(
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .size(width = 325.dp, height = 200.dp),
                    texto =  state.description,
                    onValueChange = { viewModel.descriptionImput(it) },
                    label = "Sobre mí",
                    icon = Icons.Filled.Info
                )
            }
        }
    }
    //Botones de arriba.
    TopAppBar(
        modifier = Modifier
            .height(56.dp)
            .background(Color.Transparent),
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        contentPadding = PaddingValues(bottom = 8.dp)
    ){
        //Botón de cancelar (X)
        IconButton(
            onClick = {
                navController.navigate(route = AppScreen.ProfileScreen.rutaPantalla){
                    popUpTo(AppScreen.ProfileScreen.rutaPantalla){inclusive = true}
                }
            }
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = "Icono izquierdo",
                tint = MaterialTheme.colors.primary
            )
        }

        Spacer(Modifier.weight(1f))

        //Botón de guardar cambios (✓)
        IconButton(onClick = {
            viewModel.saveImg()
            viewModel.clickEdit(viewModel.imgUri)
            navController.navigate(route = AppScreen.ProfileScreen.rutaPantalla){
                popUpTo(AppScreen.ProfileScreen.rutaPantalla){inclusive = true}
            }
        }) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Icono derecho",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}