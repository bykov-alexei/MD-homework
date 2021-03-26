package layout

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R

import com.squareup.picasso.Picasso;

class ProductsAdapter(context: Context?, c: Cursor?, flags: Int) :
    CursorAdapter(context, c, flags) {
    var p: Picasso = Picasso.Builder(context!!).build()

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.item, parent, false)
    }

    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        val picture: ImageView = view.findViewById(R.id.picture)
        val tvName: TextView = view.findViewById(R.id.name)
        val tvPrice: TextView = view.findViewById(R.id.price)
        val name: String = cursor.getString(cursor.getColumnIndex("title"))
        val price: Int = cursor.getInt(cursor.getColumnIndex("price"))
        val date = cursor.getString(cursor.getColumnIndex("date"))
        val url: String = cursor.getString(cursor.getColumnIndex("img"))
        tvName.text = "$name ($date)"
        tvPrice.text = price.toString()
        picture.setImageResource(R.drawable.user_unknown)
        p.load(url).error(R.drawable.no_image).into(picture)
    }

}