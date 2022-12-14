package com.wagarcdev.der.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SelectableChipColors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Compose DER [FilterChip].
 *
 * @param modifier the [Modifier] to be applied to this chip container.
 * @param selected [Boolean] state for this chip: either it is selected or not.
 * @param text the [String] to be displayed on this chip.
 * @param onClick callback called when the user clicks the chip.
 * @param enabled [Boolean] state to controls when this chip is enabled to click or not.
 * Default is true.
 * @param showSelectedIcon [Boolean] when an icon should be displayed if the chip is selected.
 * Default is true.
 * @param colors the [SelectableChipColors] for this chip. Default is
 * [DerChipDefaults.selectableChipColors].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DerFilterChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    showSelectedIcon: Boolean = true,
    colors: SelectableChipColors = DerChipDefaults.selectableChipColors()
) = FilterChip(
    modifier = modifier.animateContentSize(),
    selected = selected,
    onClick = onClick,
    enabled = enabled,
    colors = colors
) {
    if (showSelectedIcon) AnimatedVisibility(visible = selected) {
        Icon(
            modifier = Modifier
                .padding(start = 2.dp)
                .size(size = DerChipDefaults.SelectedIconSize),
            imageVector = Icons.Rounded.Check,
            contentDescription = null
        )
    }

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = text
    )
}

@OptIn(ExperimentalMaterialApi::class)
object DerChipDefaults {
    @Composable
    fun selectableChipColors(): SelectableChipColors = ChipDefaults.filterChipColors(
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1F),
        contentColor = MaterialTheme.colors.onPrimary,
        selectedBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.8F),
        selectedContentColor = MaterialTheme.colors.onPrimary
    )

    val SelectedIconSize = ChipDefaults.SelectedIconSize
}