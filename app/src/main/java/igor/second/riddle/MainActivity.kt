package igor.second.riddle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.flexcode.inapppurchasescompose.InAppPurchasesHelper
import igor.second.riddle.data.BannerAds
import igor.second.riddle.data.DataStoreManager
import igor.second.riddle.data.SettingData
import igor.second.riddle.ui.theme.RiddleTheme
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val dataStoreManager = DataStoreManager(this)

        setContent {

            RiddleTheme {

                val checkedState1 = remember { mutableStateOf(true) }
                val checkedState2 = remember { mutableStateOf(true) }
                val level = remember { mutableIntStateOf(1) }
                val score = remember { mutableIntStateOf(0) }
                val progress = remember { mutableIntStateOf(0) }

                LaunchedEffect(key1 = true) {
                    dataStoreManager.getSettings().collect { settings ->
                        level.intValue = settings.level
                        score.intValue = settings.score
                        progress.intValue = settings.progress
                        checkedState1.value = settings.checkedState1
                        checkedState2.value = settings.checkedState2
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMenu(
                        dataStoreManager = dataStoreManager,
                        level = level,
                        progress = progress,
                        score = score,
                        context = this,
                        checkedState1 = checkedState1,
                        checkedState2 = checkedState2
                    )
                }
            }
        }
    }
}

@Composable
fun HyperLinkText(){
    val context = LocalContext.current
    val intent = remember { Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.termsfeed.com/live/9ad1e4a2-798a-48d4-b0a8-d5d2c10b7dee")) }
    TextButton(onClick = { context.startActivity(intent) }) {
        Text(stringResource(id = R.string.privacy),
            style = MaterialTheme.typography.labelSmall)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(dataStoreManager: DataStoreManager,
             level: MutableState<Int>,
             progress: MutableState<Int>,
             score: MutableState<Int>,
             context: Context,
             checkedState1: MutableState<Boolean>,
             checkedState2: MutableState<Boolean>,
             modifier: Modifier = Modifier){

    var text2 by rememberSaveable { mutableStateOf("") }

    val text3 = stringResource(R.string.not_money)

    val text4 = stringResource(R.string.yes_money)

    val playerMusic: MediaPlayer = MediaPlayer.create(context, R.raw.fonmuz)

    val playerMp: MediaPlayer = MediaPlayer.create(context, R.raw.kaply)

    val coroutine = rememberCoroutineScope()

    val showMyInformation = rememberSaveable { mutableStateOf(false) }

    val showMySettings = rememberSaveable { mutableStateOf(false) }

    val showMyStore = rememberSaveable { mutableStateOf(false) }

    var text by rememberSaveable { mutableStateOf("") }

    val showMyGames = rememberSaveable { mutableStateOf(false) }

    val maxChar = 20

    val puzzlesImages = when (progress.value){
        0 -> R.drawable.lvl1
        1 -> R.drawable.lvl2
        2 -> R.drawable.lvl3
        3 -> R.drawable.lvl4
        4 -> R.drawable.lvl5
        5 -> R.drawable.lvl6
        6 -> R.drawable.lvl7
        7 -> R.drawable.lvl8
        8 -> R.drawable.lvl9
        9 -> R.drawable.lvl10
        10 -> R.drawable.lvl11
        11 -> R.drawable.lvl12
        12 -> R.drawable.lvl13
        13 -> R.drawable.lvl14
        14 -> R.drawable.lvl15
        15 -> R.drawable.lvl16
        16 -> R.drawable.lvl17
        17 -> R.drawable.lvl18
        18 -> R.drawable.lvl19
        19 -> R.drawable.lvl20
        20 -> R.drawable.lvl21
        21 -> R.drawable.lvl22
        22 -> R.drawable.lvl23
        23 -> R.drawable.lvl24
        24 -> R.drawable.lvl25
        25 -> R.drawable.lvl26
        26 -> R.drawable.lvl27
        27 -> R.drawable.lvl28
        28 -> R.drawable.lvl29
        29 -> R.drawable.lvl30
        30 -> R.drawable.lvl31
        31 -> R.drawable.lvl32
        32 -> R.drawable.lvl33
        33 -> R.drawable.lvl34
        34 -> R.drawable.lvl35
        35 -> R.drawable.lvl36
        36 -> R.drawable.lvl37
        37 -> R.drawable.lvl38
        else -> R.drawable.lvl39
    }

    val puzzlesString = when (progress.value){
        0 -> R.string.lvl1
        1 -> R.string.lvl2
        2 -> R.string.lvl3
        3 -> R.string.lvl4
        4 -> R.string.lvl5
        5 -> R.string.lvl6
        6 -> R.string.lvl7
        7 -> R.string.lvl8
        8 -> R.string.lvl9
        9 -> R.string.lvl10
        10 -> R.string.lvl11
        11 -> R.string.lvl12
        12 -> R.string.lvl13
        13 -> R.string.lvl14
        14 -> R.string.lvl15
        15 -> R.string.lvl16
        16 -> R.string.lvl17
        17 -> R.string.lvl18
        18 -> R.string.lvl19
        19 -> R.string.lvl20
        20 -> R.string.lvl21
        21 -> R.string.lvl22
        22 -> R.string.lvl23
        23 -> R.string.lvl24
        24 -> R.string.lvl25
        25 -> R.string.lvl26
        26 -> R.string.lvl27
        27 -> R.string.lvl28
        28 -> R.string.lvl29
        29 -> R.string.lvl30
        30 -> R.string.lvl31
        31 -> R.string.lvl32
        32 -> R.string.lvl33
        33 -> R.string.lvl34
        34 -> R.string.lvl35
        35 -> R.string.lvl36
        36 -> R.string.lvl37
        37 -> R.string.lvl38
        else -> R.string.lvl39
    }

    val answer = stringResource(puzzlesString)

    if (checkedState2.value){
        playerMusic.start()
        playerMusic.setVolume(50F, 50F)
    } else {
        playerMusic.stop()
    }

    val billingPurchaseHelper = InAppPurchasesHelper(LocalContext.current as Activity,"lite_support")
    billingPurchaseHelper.setUpBillingPurchases()

    val billingPurchaseHelper1 = InAppPurchasesHelper(LocalContext.current as Activity,"medium_support")
    billingPurchaseHelper1.setUpBillingPurchases()

    val billingPurchaseHelper2 = InAppPurchasesHelper(LocalContext.current as Activity,"hard_support")
    billingPurchaseHelper2.setUpBillingPurchases()

    val purchaseDone by billingPurchaseHelper.purchaseDone.collectAsState(false)
    val productName by billingPurchaseHelper.productName.collectAsState("")
    val purchaseStatus by billingPurchaseHelper.purchaseStatus.collectAsState("")
    val purchaseDone1 by billingPurchaseHelper.purchaseDone.collectAsState(false)
    val productName1 by billingPurchaseHelper.productName.collectAsState("")
    val purchaseStatus1 by billingPurchaseHelper.purchaseStatus.collectAsState("")
    val purchaseDone2 by billingPurchaseHelper.purchaseDone.collectAsState(false)
    val productName2 by billingPurchaseHelper.productName.collectAsState("")
    val purchaseStatus2 by billingPurchaseHelper.purchaseStatus.collectAsState("")

    Box (modifier = modifier) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.preview3),
            contentDescription = null,
            contentScale = ContentScale.FillBounds)
        Column (modifier = Modifier) {

            // Information
            Row(modifier = Modifier
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                IconButton(onClick = {
                    if (checkedState1.value){
                        playerMp.start()
                    }
                    showMyInformation.value = true
                }) {
                    Icon(Icons.Filled.Info,
                        contentDescription = "App information",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.surfaceTint)

                    if (showMyInformation.value) {
                        Dialog(onDismissRequest = { showMyInformation.value = false }) {
                            Scaffold(
                                modifier = Modifier
                                    .height(650.dp)
                                    .clip(shape = RoundedCornerShape(16.dp)),
                                topBar = {
                                    TopAppBar(
                                        colors = topAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceTint,
                                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                        ),
                                        title = {
                                            Row (modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically) {
                                                IconButton(onClick = {
                                                    if (checkedState1.value){
                                                        playerMp.start()
                                                    }
                                                    showMyInformation.value = false
                                                }) {
                                                    Icon(Icons.Filled.ArrowBack,
                                                        contentDescription = "Arrow Back",
                                                        modifier = Modifier.size(32.dp),
                                                        tint = MaterialTheme.colorScheme.onPrimary)
                                                }
                                                Spacer(modifier = Modifier.padding(16.dp))
                                                Text(
                                                    stringResource(R.string.info),
                                                    color = MaterialTheme.colorScheme.surface)
                                            }
                                        }
                                    )
                                },
                            ) { innerPadding ->
                                Column(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                                        .verticalScroll(ScrollState(1))
                                ) {
                                    Card (modifier = Modifier.fillMaxWidth()) {
                                        Text(text = stringResource(R.string.rules1),
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            modifier = Modifier.align(Alignment.CenterHorizontally))
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        topStart = 90.dp,
                                                        topEnd = 16.dp,
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                ),
                                            painter = painterResource(R.drawable.rules1),
                                            contentDescription = "")
                                    }
                                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                                    Card (modifier = Modifier.fillMaxWidth()) {
                                        Text(text = stringResource(R.string.rules2),
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            modifier = Modifier.align(Alignment.CenterHorizontally))
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        topStart = 90.dp,
                                                        topEnd = 16.dp,
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                ),
                                            painter = painterResource(R.drawable.rules2),
                                            contentDescription = "")
                                    }
                                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                                    Card (modifier = Modifier.fillMaxWidth()) {
                                        Text(text = stringResource(R.string.rules3),
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            modifier = Modifier.align(Alignment.CenterHorizontally))
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        topStart = 90.dp,
                                                        topEnd = 16.dp,
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                ),
                                            painter = painterResource(R.drawable.rules3),
                                            contentDescription = "")
                                    }
                                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                                    Card (modifier = Modifier.fillMaxWidth()) {
                                        Text(text = stringResource(R.string.rules4),
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            modifier = Modifier.align(Alignment.CenterHorizontally))
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        topStart = 90.dp,
                                                        topEnd = 16.dp,
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                ),
                                            painter = painterResource(R.drawable.rules4),
                                            contentDescription = "")
                                    }
                                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                                    Card (modifier = Modifier.fillMaxWidth()) {
                                        Text(text = stringResource(R.string.rules5),
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            modifier = Modifier.align(Alignment.CenterHorizontally))
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        topStart = 90.dp,
                                                        topEnd = 16.dp,
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                ),
                                            painter = painterResource(R.drawable.rules5),
                                            contentDescription = "")
                                    }
                                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                                    Card (modifier = Modifier.fillMaxWidth()) {
                                        Text(text = stringResource(R.string.rules6),
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.surfaceTint,
                                            modifier = Modifier.align(Alignment.CenterHorizontally))
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        topStart = 90.dp,
                                                        topEnd = 16.dp,
                                                        bottomStart = 16.dp,
                                                        bottomEnd = 16.dp
                                                    )
                                                ),
                                            painter = painterResource(R.drawable.rules6),
                                            contentDescription = "")
                                    }
                                }
                            }
                        }
                    }
                }
                IconButton(onClick = {
                    if (checkedState1.value){
                        playerMp.start()
                    }
                    exitProcess(1)
                }) {
                    Icon(
                        Icons.Filled.ExitToApp,
                        contentDescription = "Exit to app",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            }
            // Information end

            // Settings
            Row(modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    if (checkedState1.value){
                        playerMp.start()
                    }
                    showMySettings.value = true
                }) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "App settings",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
                if (showMySettings.value) {
                    Dialog(onDismissRequest = { showMySettings.value = false }) {
                        Scaffold(
                            modifier = Modifier
                                .height(200.dp)
                                .clip(shape = RoundedCornerShape(16.dp)),
                            topBar = {
                                TopAppBar(
                                    colors = topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceTint,
                                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    title = {
                                        Row (
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()){
                                            IconButton(onClick = {
                                                if (checkedState1.value){
                                                    playerMp.start()
                                                }
                                                showMySettings.value = false
                                            }) {
                                                Icon(
                                                    Icons.Filled.ArrowBack,
                                                    contentDescription = "App settings",
                                                    modifier = Modifier.size(32.dp),
                                                    tint = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }
                                            Spacer(modifier = Modifier.padding(24.dp))
                                            Text(
                                                stringResource(R.string.settings),
                                                color = MaterialTheme.colorScheme.surface)
                                        }
                                    }
                                )
                            },
                        ) { innerPadding ->
                            Column (modifier = Modifier
                                .padding(innerPadding)
                                .padding(16.dp)) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        stringResource(R.string.music),
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.surfaceTint
                                    )
                                    Spacer(modifier = Modifier.padding(end = 124.dp))
                                    Switch(
                                        checked = checkedState2.value,
                                        onCheckedChange = {
                                            checkedState2.value = it
                                            if (checkedState1.value){
                                                playerMp.start()
                                            }
                                            coroutine.launch {
                                                dataStoreManager.saveSettings(
                                                    SettingData(
                                                        checkedState1 = checkedState1.value,
                                                        checkedState2 = checkedState2.value,
                                                        level = level.value,
                                                        score = score.value,
                                                        progress = progress.value
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        stringResource(R.string.sounds),
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.surfaceTint
                                    )
                                    Spacer(modifier = Modifier.padding(end = 142.dp))
                                    Switch(
                                        checked = checkedState1.value,
                                        onCheckedChange = {
                                            checkedState1.value = it
                                            if (checkedState1.value){
                                                playerMp.start()
                                            }
                                            coroutine.launch {
                                                dataStoreManager.saveSettings(
                                                    SettingData(
                                                        checkedState1 = checkedState1.value,
                                                        checkedState2 = checkedState2.value,
                                                        level = level.value,
                                                        score = score.value,
                                                        progress = progress.value
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            // Settings end

            // Store start
            Row(modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            ) {
                IconButton(onClick = {
                    if (checkedState1.value){
                        playerMp.start()
                    }
                    showMyStore.value = true
                }) {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = "App store",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.surfaceTint)
                }
                if (showMyStore.value) {
                    Dialog(onDismissRequest = { showMyStore.value = false }) {
                        Scaffold(
                            modifier = Modifier
                                .height(650.dp)
                                .clip(shape = RoundedCornerShape(16.dp)),
                            topBar = {
                                TopAppBar(
                                    colors = topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceTint,
                                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    title = {
                                        Row (
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()) {
                                            IconButton(onClick = {
                                                if (checkedState1.value){
                                                    playerMp.start()
                                                }
                                                showMyStore.value = false
                                            }) {
                                                Icon(
                                                    Icons.Filled.ArrowBack,
                                                    contentDescription = "App store",
                                                    modifier = Modifier.size(32.dp),
                                                    tint = MaterialTheme.colorScheme.onPrimary)
                                            }
                                            Spacer(modifier = Modifier.padding(16.dp))
                                            Text(
                                                stringResource(R.string.store),
                                                color = MaterialTheme.colorScheme.surface)
                                        }
                                    }
                                )
                            },
                        ) { innerPadding ->
                            Column (modifier = Modifier
                                .padding(innerPadding)
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                                .verticalScroll(ScrollState(1))) {
                                Card (modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        stringResource(R.string.coin1),
                                        fontSize = 16.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp, top = 8.dp)
                                            .clip(
                                                shape = RoundedCornerShape(
                                                    topStart = 90.dp,
                                                    topEnd = 16.dp,
                                                    bottomStart = 16.dp,
                                                    bottomEnd = 16.dp
                                                )
                                            ),
                                        painter = painterResource(R.drawable.money_one),
                                        contentDescription = "")
                                    Button(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .wrapContentSize(),
                                        //Нажатие и логика покупки
                                        onClick = {
                                            billingPurchaseHelper.initializePurchase()
                                            score.value += 40
                                            coroutine.launch {
                                                dataStoreManager
                                                    .saveSettings(
                                                        SettingData(
                                                            level = level.value,
                                                            progress = progress.value,
                                                            score = score.value,
                                                            checkedState1 = checkedState1.value,
                                                            checkedState2 = checkedState2.value
                                                        )
                                                    )
                                            }
                                        },
                                        shape = RoundedCornerShape(32.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.surface,
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        ),
                                        enabled = purchaseDone
                                    ) {
                                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                        Text(
                                            stringResource(R.string.buy1),
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.padding(16.dp))
                                Card (modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        stringResource(R.string.coin2),
                                        fontSize = 16.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp, top = 8.dp)
                                            .clip(
                                                shape = RoundedCornerShape(
                                                    topStart = 90.dp,
                                                    topEnd = 16.dp,
                                                    bottomStart = 16.dp,
                                                    bottomEnd = 16.dp
                                                )
                                            ),
                                        painter = painterResource(R.drawable.money_two),
                                        contentDescription = "")
                                    Button(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .wrapContentSize(),

                                        //Нажатие и логика покупки
                                        onClick = {
                                            billingPurchaseHelper1.initializePurchase()
                                            score.value += 110
                                            coroutine.launch {
                                                dataStoreManager
                                                    .saveSettings(
                                                        SettingData(
                                                            level = level.value,
                                                            progress = progress.value,
                                                            score = score.value,
                                                            checkedState1 = checkedState1.value,
                                                            checkedState2 = checkedState2.value
                                                        )
                                                    )
                                            }
                                        },
                                        shape = RoundedCornerShape(32.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.surface,
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        ),
                                        enabled = purchaseDone1
                                    ) {
                                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                        Text(
                                            stringResource(R.string.buy2),
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.padding(16.dp))
                                Card (modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        stringResource(R.string.coin3),
                                        fontSize = 16.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp, top = 8.dp)
                                            .clip(
                                                shape = RoundedCornerShape(
                                                    topStart = 90.dp,
                                                    topEnd = 16.dp,
                                                    bottomStart = 16.dp,
                                                    bottomEnd = 16.dp
                                                )
                                            ),
                                        painter = painterResource(R.drawable.money_three),
                                        contentDescription = "")
                                    Button(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .wrapContentSize(),
                                        //Нажатие и логика покупки
                                        onClick = {
                                            billingPurchaseHelper2.initializePurchase()
                                            score.value += 250
                                            coroutine.launch {
                                                dataStoreManager
                                                    .saveSettings(
                                                        SettingData(
                                                            level = level.value,
                                                            progress = progress.value,
                                                            score = score.value,
                                                            checkedState1 = checkedState1.value,
                                                            checkedState2 = checkedState2.value
                                                        )
                                                    )
                                            }
                                        },
                                        shape = RoundedCornerShape(32.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.surface,
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        ),
                                        enabled = purchaseDone2
                                    ) {
                                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                        Text(
                                            stringResource(R.string.buy3),
                                            fontSize = 16.sp,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Store end

            if (showMyGames.value) {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${score.value}",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.surfaceTint)
                    Spacer(modifier = Modifier.padding(3.dp))
                    Image(
                        modifier = Modifier.fillMaxSize(0.08f),
                        painter = painterResource(R.drawable.money),
                        contentDescription = "")
                    IconButton(onClick = {
                        if (checkedState1.value){
                            playerMp.start()
                        }
                        showMyStore.value = true 
                    }) {
                        Icon (
                            Icons.Filled.Add,
                            contentDescription = "Add to money",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.surfaceTint
                        )
                    }
                }
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        if (checkedState1.value){
                            playerMp.start()
                        }
                        if (score.value >= 10){
                            score.value -= 10
                            text = answer
                            text2 = text4
                        } else {
                            text2 = text3
                        }
                        coroutine.launch {
                            dataStoreManager
                                .saveSettings(
                                    SettingData(
                                        level = level.value,
                                        progress = progress.value,
                                        score = score.value,
                                        checkedState1 = checkedState1.value,
                                        checkedState2 = checkedState2.value
                                    )
                                )
                        }
                    }) {
                        Text(stringResource(R.string.add))
                    }
                }
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Text(text = text2,
                        color = MaterialTheme.colorScheme.surfaceTint)
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.padding(16.dp),
                        painter = painterResource(puzzlesImages),
                        contentDescription = "1"
                    )
                    Row  {
                        TextField(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            value = text,
                            onValueChange = { if (it.length <= maxChar) text = it },
                            label = { Text(stringResource(R.string.answer_context)) },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                                capitalization = KeyboardCapitalization.None
                            ),
                            maxLines = 1
                        )
                        IconButton(onClick = {
                            if (checkedState1.value){
                                playerMp.start()
                            }
                            if (text.lowercase(Locale.ROOT).trim() == answer) {
                                text = ""
                                text2 = ""
                                level.value += 1
                                progress.value += 1
                                if (progress.value in 0..19){
                                    score.value += 1
                                } else if (progress.value in 20..59){
                                    score.value += 2
                                } else if (progress.value > 59){
                                    score.value += 3
                                }
                                coroutine.launch {
                                    dataStoreManager
                                        .saveSettings(
                                            SettingData(
                                                level = level.value,
                                                progress = progress.value,
                                                score = score.value,
                                                checkedState1 = checkedState1.value,
                                                checkedState2 = checkedState2.value
                                            )
                                        )
                                }
                            } else {
                                text = ""
                                return@IconButton
                            }
                        }) {
                            Icon(
                                Icons.Filled.Send,
                                contentDescription = "App store",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }
                    HyperLinkText()
                }
            } else {
                Column (modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = {
                        showMyGames.value = true
                        if (checkedState1.value){
                            playerMp.start()
                        }
                                     },
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.surfaceTint),
                            colors = ButtonDefaults.outlinedButtonColors
                                (contentColor = MaterialTheme.colorScheme.surfaceTint)) {
                        Text(
                            stringResource(id = R.string.start),
                            modifier = Modifier.padding(16.dp),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.surfaceTint,
                        )
                    }
                }
            }
        }
    }
    Column (modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
        BannerAds(id = R.string.banner_ads)
    }
}

