package com.example.jetnews.ui.jetnews

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnews.R

@Preview
@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_title)) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            Icon(
                Icons.Rounded.Palette, null,
                Modifier.padding(horizontal = 12.dp)
            )
        }
    )
}
