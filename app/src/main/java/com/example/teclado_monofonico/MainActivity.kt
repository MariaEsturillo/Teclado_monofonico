package com.example.teclado_monofonico

import android.content.pm.ActivityInfo
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teclado_monofonico.ui.theme.Teclado_monofonicoTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        enableEdgeToEdge()
        setContent {
            Teclado_monofonicoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Filas()
                    NavigationBar { Modifier
                        .background(Color.Transparent)}
                    Greeting(
                        name = "",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

    }


    }

    @Composable
    fun BotonesReproducirNotas(string: String, int: Int) {
        val context = LocalContext.current
        var soundPool by remember { mutableStateOf<SoundPool?>(null) }
        var soundId by remember { mutableStateOf(0) }
        val sonidos =
            arrayOf(R.raw.doo, R.raw.re, R.raw.mi, R.raw.fa, R.raw.sol, R.raw.la, R.raw.si)

        DisposableEffect(Unit) {

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()


            soundPool = SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build()


            soundId = soundPool?.load(context, sonidos[int], 1) ?: 0


            onDispose {
                soundPool?.release()
                soundPool = null
            }
        }
        var buttonColor by remember { mutableStateOf(Color.Black) }

        Button(
            onClick = {
                soundPool?.play(soundId, 1f, 1f, 1, 0, 1f) // Reproduce el sonido

                buttonColor =
                    if (buttonColor == Color(0xFFbf90f5)) Color(0xFF08fc7e) else Color(0xFFbf90f5)
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            contentPadding = PaddingValues(20.dp),


            ) {
            Icon(Icons.Rounded.PlayArrow, contentDescription = null, Modifier.size(40.dp))
            Text(string, fontSize = 20.sp)
        }
    }

    @Composable
    fun Filas(
    ) {
        val arrayNotas = arrayOf("DO", "RE", "MI", "FA", "SOL", "LA", "SI")
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.fondoapp),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(40.dp, 0.dp, 65.dp, 0.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,


                ) {

                for (i in 0..6) {

                    BotonesReproducirNotas(arrayNotas[i], i)

                }

            }
        }

    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Filas()

    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Teclado_monofonicoTheme {
            Filas()
        }
    }

