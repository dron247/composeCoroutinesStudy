package org.dementiev.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.dementiev.data.entity.NewsItem

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onItemClick: (NewsItem) -> Unit = {}
) {
    val state = viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        when (state.value) {
            is NewsState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            is NewsState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = "Ошибка загрузки",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.loadData() }
                    ) {
                        Text(text = "Повторить")
                    }
                }
            }
            is NewsState.Content -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(
                        isRefreshing = state.value is NewsState.Loading
                    ), // no-op
                    onRefresh = { viewModel.loadData() },
                    modifier = Modifier.fillMaxSize()
                ) {
                    val content = state.value as NewsState.Content
                    LazyColumn {
                        items(items = content.news, itemContent = { newsItem ->
                            Row(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .height(48.dp)
                                    .clickable {
                                        onItemClick(newsItem)
                                    }
                            ) {
                                Text(
                                    text = newsItem.title,
                                    modifier = Modifier
                                        .padding(start = 16.dp, end = 16.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        })
                    }
                }
            }
        }
    }
}