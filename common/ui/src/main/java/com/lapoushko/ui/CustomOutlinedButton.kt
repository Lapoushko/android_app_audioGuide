package com.lapoushko.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.primaryLight
import com.lapoushko.ui.theme.tertiaryContainerLight

/**
 * @author Lapoushko
 */
@Composable
fun CustomOutlinedButton(
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier.size(width = 170.dp, height = 54.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) tertiaryContainerLight else Color.Transparent,
            contentColor = primaryLight,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(text = text, textAlign = TextAlign.Center, style = Typography.labelLarge)
        }
    }
}