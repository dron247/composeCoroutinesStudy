package org.dementiev.composestudy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dementiev.composestudy.ui.theme.ComposeStudyTheme
import org.dementiev.data.NewsRepository
import org.dementiev.data.Result
import org.dementiev.data.entity.NewsItem
import org.dementiev.news.NewsScreen
import org.dementiev.news.NewsViewModel

class MainActivity : ComponentActivity() {
    private val newsRepository by lazy {
        NewsRepository.create(this)
    }

    private val newsViewModel by lazy {
        NewsViewModel(newsRepository) // TODO: use factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsScreen(newsViewModel) { clickedItem ->
                        Toast.makeText(
                            this,
                            "Clicked item id=${clickedItem.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val vm = NewsViewModel(
        object : NewsRepository {
            override suspend fun getNews(): Flow<Result<List<NewsItem>, Throwable>> {
                return flow {
                    emit(
                        Result.Success(
                            listOf(
                                NewsItem(
                                    id = "1",
                                    title = "Hello",
                                    content = "Hello world"
                                )
                            )
                        )
                    )
                }
            }
        }
    )

    ComposeStudyTheme {
        NewsScreen(vm)
    }
}