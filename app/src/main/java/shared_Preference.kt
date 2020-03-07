import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.salesrecord.LoginActivity
import com.example.salesrecord.R

class shared_Preference: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_controlmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            var per=getSharedPreferences("myPref", Context.MODE_PRIVATE)
            var edit=per.edit()
            edit.clear()
            edit.commit()

            Toast.makeText(this@shared_Preference,"Logged out Successfully!", Toast.LENGTH_LONG).show()
            var inte= Intent(this@shared_Preference, LoginActivity::class.java)
            startActivity(inte)
            finish()
        }
        else{
            Toast.makeText(this@shared_Preference,"Random Option!", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}