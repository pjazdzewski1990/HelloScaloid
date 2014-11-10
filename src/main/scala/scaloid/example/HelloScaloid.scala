package scaloid.example

import org.scaloid.common._
import org.scaloid.common.Implicits
import org.scaloid.util.Configuration._

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.AdapterView
import android.view.View

case class ScalacHakker(name: String, li: Uri) {
  override def toString = name
}

class HelloScaloid extends SActivity {

  val items = Array(
    ScalacHakker("Jan Ziniewicz", "https://pl.linkedin.com/pub/jan-ziniewicz/49/b4b/972"), // implicit conversion from String to Uri
    ScalacHakker("Łukasz Kuczera", "https://pl.linkedin.com/in/lkuczera/"),
    ScalacHakker("Jakub Czuchnowski", "https://pl.linkedin.com/pub/jakub-czuchnowski/15/161/31"),
    ScalacHakker("Patryk Jażdżewski", "https://www.linkedin.com/pub/patryk-ja%C5%BCd%C5%BCewski/76/b15/774")
  )

  onCreate {
    contentView = new SVerticalLayout {
      if(!landscape) this += new SVerticalLayout {
        SImageView().setImageResource(R.drawable.logo)
        STextView("Scalac Team - Scaloid") textSize(25.dip)
      } else {
        STextView("Scalac Team - Scaloid") textSize(35.dip)
      }

      SListView() adapter(createListAdapter(items)) onItemClick((_: AdapterView[_], _: View, position: Int, _: Long) => {
        openUri(items(position).li)
      })

      SButton("Join Us!").onClick(handleClick)
      if(landscape) this += new SLinearLayout {
        SImageView().setImageResource(R.drawable.logo)
      }
    } padding 15.dip
  }

  def createListAdapter(items: Array[ScalacHakker]) = {
    SArrayAdapter(items).style(_.textColor(Color.WHITE))
  }

  def handleClick = {
    val email = new Intent(Intent.ACTION_SEND)
    email.putExtra(Intent.EXTRA_EMAIL, Array("info@scalac.io"))
      .putExtra(Intent.EXTRA_SUBJECT, "Join Scalac")
      .setType("message/rfc822")
    startActivity(Intent.createChooser(email, "Choose an Email client :"))
  }

}
