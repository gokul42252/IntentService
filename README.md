
<h1>Best Practice IntentService Android Example</h1>
<img src="http://thoughtnerds.com/wp-content/uploads/2018/02/Screenshot-72-300x97.png" alt="" width="721" height="233" class="alignnone wp-image-632" />

<span>The </span><code><a href="https://developer.android.com/reference/android/app/IntentService.html">IntentService</a></code><span> class provides a straightforward structure for running an operation on a single background thread. This allows it to handle long-running operations without affecting your user interface's responsiveness. Also, an </span><code><a href="https://developer.android.com/reference/android/app/IntentService.html">IntentService</a></code><span> isn't affected by most user interface lifecycle events, so it continues to run in circumstances that would shut down an </span><code><a href="https://developer.android.com/reference/android/os/AsyncTask.html">AsyncTask.</a></code>This tutorial shows Best Practice IntentService Android Example

An<span> </span><code><a href="https://developer.android.com/reference/android/app/IntentService.html">IntentService</a></code><span> </span>has a few limitations:
<ul>
 	<li>It can't interact directly with your user interface. To put its results in the UI, you have to send them to an<span> </span><code><a href="https://developer.android.com/reference/android/app/Activity.html">Activity</a></code>.</li>
 	<li>Work requests run sequentially. If an operation is running in an<span> </span><code><a href="https://developer.android.com/reference/android/app/IntentService.html">IntentService</a></code>, and you send it another request, the request waits until the first operation is finished.</li>
 	<li>An operation running on an<span> </span><code><a href="https://developer.android.com/reference/android/app/IntentService.html">IntentService</a></code><span> </span>can't be interrupted.</li>
</ul>
However, in most cases an<span> </span><code><a href="https://developer.android.com/reference/android/app/IntentService.html">IntentService</a></code><span> </span>is the preferred way to perform simple background operations.

1)Create a WorkerIntentService.java extending IntentService
<pre><span>package </span>com.cretlabs.intentservice<span>;
</span><span>
</span><span>import </span>android.app.IntentService<span>;
</span><span>import </span>android.content.Context<span>;
</span><span>import </span>android.content.Intent<span>;
</span><span>import </span>android.os.Bundle<span>;
</span><span>import </span>android.support.annotation.<span>Nullable</span><span>;
</span><span>
</span><span>/**
</span><span> * Created by Gokul on 2/25/2018.
</span><span> */
</span><span>
</span><span>public class </span>WorkerIntentService <span>extends </span>IntentService {
    <span>private static final </span>String <span>TAG </span>= <span>"WorkerIntentService"</span><span>;
</span><span>    public static final </span>String <span>RECEIVER </span>= <span>"receiver"</span><span>;
</span><span>    public static final int </span><span>SHOW_RESULT </span>= <span>123</span><span>;
</span><span>    </span><span>/**
</span><span>     * Result receiver object to send results
</span><span>     */
</span><span>    </span><span>private </span>android.support.v4.os.ResultReceiver <span>mResultReceiver</span><span>;
</span><span>
</span><span>    </span><span>/**
</span><span>     * Actions download
</span><span>     */
</span><span>    </span><span>public static final </span>String <span>ACTION_DOWNLOAD </span>= <span>"action.DOWNLOAD_DATA"</span><span>;
</span><span>
</span><span>    </span><span>/**
</span><span>     * Creates an IntentService.  Invoked by your subclass's constructor.
</span><span>     *
</span><span>     */
</span><span>    </span><span>public </span><span>WorkerIntentService</span>() {
        <span>super</span>(<span>TAG</span>)<span>;
</span><span>    </span>}



    <span>@Override
</span><span>    </span><span>protected void </span><span>onHandleIntent</span>(<span>@Nullable </span>Intent intent) {
        <span>if </span>(intent.getAction() != <span>null</span>) {
            <span>switch </span>(intent.getAction()) {
                <span>case </span><span>ACTION_DOWNLOAD</span>:
                    <span>mResultReceiver </span>= intent.getParcelableExtra(<span>RECEIVER</span>)<span>;
</span><span>                    for</span>(<span>int </span>i=<span>0</span><span>;</span>i&lt;<span>10</span><span>;</span>i++){
                        <span>try </span>{
                            Thread.<span>sleep</span>(<span>1000</span>)<span>;
</span><span>                            </span>Bundle bundle = <span>new </span>Bundle()<span>;
</span><span>                            </span>bundle.putString(<span>"data"</span><span>,</span>String.<span>format</span>(<span>"Showing From Intent Service %d"</span><span>, </span>i))<span>;
</span><span>                            </span><span>mResultReceiver</span>.send(<span>SHOW_RESULT</span><span>, </span>bundle)<span>;
</span><span>                        </span>} <span>catch </span>(InterruptedException e) {
                            e.printStackTrace()<span>;
</span><span>                        </span>}
                    }
                    <span>break;
</span><span>            </span>}
        }
    }
}</pre>
&nbsp;

2)Create a <span>ResultReceiver.java to communicate to activity from background</span>
<pre><span>public class </span>ResultReceiver <span>extends </span>android.support.v4.os.ResultReceiver {
    <span>private static final </span>String <span>TAG </span>= <span>"ResultReceiver"</span><span>;
</span><span>    private </span>Receiver <span>mReceiver</span><span>;
</span><span>
</span><span>    </span><span>/**
</span><span>     * Create a new ResultReceive to receive results.  Your
</span><span>     * {</span><span>@link </span><span>#onReceiveResult} method will be called from the thread running
</span><span>     * </span><span>&lt;var&gt;</span><span>handler</span><span>&lt;/var&gt; </span><span>if given, or from an arbitrary thread if null.
</span><span>     *
</span><span>     * </span><span>@param </span><span>handler </span><span>the handler object
</span><span>     */
</span><span>    </span><span>public </span><span>ResultReceiver</span>(Handler handler) {
        <span>super</span>(handler)<span>;
</span><span>    </span>}

    <span>public void </span><span>setReceiver</span>(Receiver receiver) {
        <span>mReceiver </span>= receiver<span>;
</span><span>    </span>}


    <span>@Override
</span><span>    </span><span>protected void </span><span>onReceiveResult</span>(<span>int </span>resultCode<span>, </span>Bundle resultData) {
        <span>if </span>(<span>mReceiver </span>!= <span>null</span>) {
            <span>mReceiver</span>.onReceiveResult(resultCode<span>, </span>resultData)<span>;
</span><span>        </span>}
    }

    <span>public interface </span>Receiver {
        <span>void </span><span>onReceiveResult</span>(<span>int </span>resultCode<span>, </span>Bundle resultData)<span>;
</span><span>    </span>}
}</pre>
3)Create MainActivity.java
<pre><span>public class </span>MainActivity <span>extends </span>AppCompatActivity <span>implements </span>ResultReceiver.Receiver {
    ResultReceiver <span>mResultReceiver</span><span>;
</span><span>    </span>TextView <span>mTextView</span><span>;
</span><span>
</span><span>    </span><span>@Override
</span><span>    </span><span>protected void </span><span>onCreate</span>(Bundle savedInstanceState) {
        <span>super</span>.onCreate(savedInstanceState)<span>;
</span><span>        </span>setContentView(R.layout.<span>activity_main</span>)<span>;
</span><span>        </span><span>mResultReceiver </span>= <span>new </span>ResultReceiver(<span>new </span>Handler())<span>;
</span><span>        </span><span>mResultReceiver</span>.setReceiver(<span>this</span>)<span>;
</span><span>        </span><span>mTextView</span> = findViewById(R.id.<span>tv_data</span>)<span>;
</span><span>        </span>showDataFromBackground(MainActivity.<span>this, </span><span>mResultReceiver</span>)<span>;
</span><span>    </span>}

    <span>private void </span><span>showDataFromBackground</span>(Context context <span>, </span>ResultReceiver mResultReceiver) {
            Intent intent = <span>new </span>Intent( context<span>, </span>WorkerIntentService.<span>class</span>)<span>;
</span><span>            </span>intent.putExtra(<span>RECEIVER</span><span>, </span>mResultReceiver)<span>;
</span><span>            </span>intent.setAction(<span>ACTION_DOWNLOAD</span>)<span>;
</span><span>            </span>startService(intent)<span>;
</span><span>    </span>}

    <span>public void </span><span>showData</span>(String data) {
        <span>mTextView</span>.setText(String.<span>format</span>(<span>"%s</span><span>\n</span><span>%s"</span><span>, </span><span>mTextView</span>.getText()<span>, </span>data))<span>;
</span><span>    </span>}

    <span>@Override
</span><span>    </span><span>public void </span><span>onReceiveResult</span>(<span>int </span>resultCode<span>, </span>Bundle resultData) {
        <span>switch </span>(resultCode) {
            <span>case </span><span>SHOW_RESULT</span>:
                <span>if </span>(resultData != <span>null</span>) {
                    showData(resultData.getString(<span>"data"</span>))<span>;
</span><span>                </span>}
                <span>break;
</span><span>        </span>}
    }
}

</pre>
5)Create main_activity.xml
<pre><span>&lt;?</span><span>xml version=</span><span>"1.0" </span><span>encoding=</span><span>"utf-8"</span><span>?&gt;
</span><span>&lt;android.support.constraint.ConstraintLayout </span><span>xmlns:</span><span>android</span><span>=</span><span>"http://schemas.android.com/apk/res/android"
</span><span>    </span><span>xmlns:</span><span>app</span><span>=</span><span>"http://schemas.android.com/apk/res-auto"
</span><span>    </span><span>xmlns:</span><span>tools</span><span>=</span><span>"http://schemas.android.com/tools"
</span><span>    </span><span>android</span><span>:layout_width=</span><span>"match_parent"
</span><span>    </span><span>android</span><span>:layout_height=</span><span>"match_parent"
</span><span>    </span><span>tools</span><span>:context=</span><span>"com.cretlabs.intentservice.MainActivity"</span><span>&gt;
</span><span>
</span><span>
</span><span>    &lt;TextView
</span><span>        </span><span>android</span><span>:id=</span><span>"@+id/tv_data"
</span><span>        </span><span>android</span><span>:layout_width=</span><span>"wrap_content"
</span><span>        </span><span>android</span><span>:layout_height=</span><span>"wrap_content"
</span><span>        </span><span>app</span><span>:layout_constraintBottom_toBottomOf=</span><span>"parent"
</span><span>        </span><span>app</span><span>:layout_constraintLeft_toLeftOf=</span><span>"parent"
</span><span>        </span><span>app</span><span>:layout_constraintRight_toRightOf=</span><span>"parent"
</span><span>        </span><span>app</span><span>:layout_constraintTop_toTopOf=</span><span>"parent" </span><span>/&gt;
</span><span>
</span><span>&lt;/android.support.constraint.ConstraintLayout&gt;
</span></pre>

Article is originally published in http://thoughtnerds.com/best-practice-intentservice-android-example/
