package scaloid.scalac

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
    ScalacHakker("Jan Ziniewicz",       "https://pl.linkedin.com/pub/jan-ziniewicz/49/b4b/972"), // implicit conversion from String to Uri
    ScalacHakker("Łukasz Kuczera",      "https://pl.linkedin.com/in/lkuczera/"),
    ScalacHakker("Jakub Czuchnowski",   "https://pl.linkedin.com/pub/jakub-czuchnowski/15/161/31"),
    ScalacHakker("Patryk Jażdżewski",   "https://www.linkedin.com/pub/patryk-ja%C5%BCd%C5%BCewski/76/b15/774"),
    ScalacHakker("Sławomir Wójcik",     "https://pl.linkedin.com/pub/s%C5%82awomir-w%C3%B3jcik/3a/a35/3bb"),
    ScalacHakker("Michał Kiędyś",       "http://pl.linkedin.com/in/mkiedys%E2%80%8E"),
    ScalacHakker("Arek Kaczyński",      "https://pl.linkedin.com/pub/arkadiusz-kaczy%C5%84ski/58/60/231%E2%80%8E"),
    ScalacHakker("Maciej Greń",         "http://pl.linkedin.com/in/maciejgren%E2%80%8E"),
    ScalacHakker("Łukasz Gąsior",       "https://pl.linkedin.com/pub/%C5%82ukasz-g%C4%85sior/86/809/b01/%E2%80%8E"),
    ScalacHakker("Patryk Zakrzewski",   "http://pl.linkedin.com/pub/patryk-zakrzewski/98/370/812/en"),
    ScalacHakker("Piotr Zientarski",    "https://pl.linkedin.com/pub/piotr-zientarski/99/22b/1a8"),
    ScalacHakker("Adam Nadoba",         "https://www.linkedin.com/pub/adam-nadoba/9b/95b/584"),
    ScalacHakker("Patryk Grandt",       "https://www.linkedin.com/pub/patryk-grandt/28/6b8/332"),
    ScalacHakker("Wojciech Szymański",  "https://www.linkedin.com/pub/wojciech-szyma%C5%84ski/a4/308/153")
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
