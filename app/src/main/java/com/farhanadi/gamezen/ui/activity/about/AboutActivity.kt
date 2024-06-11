import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farhanadi.gamezen.R
import com.farhanadi.gamezen.ui.theme.DarkPurple

@Composable
fun AboutActivity(
    modifier: Modifier = Modifier,
    imagePaddingTop: Dp = 25.dp,
    imagePaddingBottom: Dp = 25.dp,
) {
    val image = painterResource(id = R.drawable.profil_farhan)
    val instagramIcon = painterResource(id = R.drawable.instagram_logo)
    val githubIcon = painterResource(id = R.drawable.github_logo)
    val linkedinIcon = painterResource(id = R.drawable.linkedin_logo)

    val backgroundColor = Color(0xFFA7A1BC)
    val textColor = Color(0xFF18122B)
    val cardBackgroundColor = Color.White
    val cornerRadius = 14.dp
    val imageSize = 160.dp
    val iconSize = 50.dp
    val spacerHeight = 24.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(vertical = imagePaddingTop, horizontal = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = imagePaddingTop, horizontal = 20.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(cardBackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = imagePaddingBottom)
            ) {
                Box(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = image,
                        contentDescription = "about_image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(spacerHeight))

                Text(
                    text = stringResource(id = R.string.name_developer_farhan),
                    color = textColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(id = R.string.email_developer_farhan),
                    color = textColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(id = R.string.status_developer_farhan),
                    color = textColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconWithLink(instagramIcon, "https://www.instagram.com/farhan_an0717/", iconSize)
                    IconWithLink(githubIcon, "https://github.com/Hanzein", iconSize)
                    IconWithLink(linkedinIcon, "https://www.linkedin.com/in/farhanadinugraha07/", iconSize)
                }
            }
        }
    }
}

@Composable
fun IconWithLink(icon: Painter, url: String, iconSize: Dp) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .size(iconSize)
            .clickable {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
