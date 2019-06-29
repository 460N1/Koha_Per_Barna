package com.a60n1.kohabarna.db

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.a60n1.kohabarna.R
import com.a60n1.kohabarna.activities.ShtoNdrysho
import com.a60n1.kohabarna.db.SQLHelper.Companion.TB_name

///QITU MBUSHET RECYCLERVIEW ME TE DHENA PREJ DB
class Adapter(var context: Context, data: ArrayList<Barna>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var data: List<Barna>

    init {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_barna,
                parent,
                false
            )
        )
        //^sapo te krijohet recyclerView, vendose nje dizajn te definuar si item_barna ne parent
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //^kur te na duhet me mbush me te dhena
        holder.id.text = data[position].id
        holder.emri.text = data[position].emri
        holder.pershkrimi.text = data[position].pershkrimi
        holder.dataF.text = data[position].dataF
        holder.dataM.text = data[position].dataM
        holder.doktori.text = data[position].doktori
        //^vendosi te gjitha te dhenat e lexuara per ate barn qe eshte ne radh
        holder.btnDel.setOnClickListener {
            //^definojme se qka do ndodh kur t'e prekum Fshij
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.fshirje_title))
            builder.setMessage(context.getString(R.string.r_u_sure))
            builder.setIcon(R.drawable.ic_delete)
            //^percaktojme titullin, mesazhin, ikonen
            builder.setPositiveButton("PO") { _, _ ->
                SQLHelper(context)
                    .writableDatabase
                    .delete(
                        TB_name,
                        "id = ?",
                        arrayOf(holder.id.text.toString())
                    )
                //^kur te preket PO, thirret funksioni per delete permes id
                holder.cardItem.visibility = View.GONE
                //^fshehet nga pamja (ne vend qe me u ba refresh lista e barnave qe jon ne db se ngarkon)
                Toast.makeText(context, context.getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Jo") { _, _ ->
                Toast.makeText(context, context.getString(R.string.delete_fail), Toast.LENGTH_SHORT).show()
                //^kur te preket JO
            }
            builder.setNeutralButton("Kthehu") { _, _ ->
                //^kur te preket Kthehu
                Toast.makeText(context, context.getString(R.string.delete_cancel), Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()
            //^krijo dialogun siq u definua deri tash
            dialog.show()
            //^shfaqe
        }
        holder.btnEdit.setOnClickListener {
            //^kur te preket butoni edit i nje barne te regjistrume
            val intent = Intent(context, ShtoNdrysho::class.java)
            //^krijohet nje intent qe tregon se perfundimisht qellimi eshte te hapet ShtoNdrysho
            intent.putExtra("id", holder.id.text.toString())
            intent.putExtra("emri", holder.emri.text.toString())
            intent.putExtra("pershkrimi", holder.pershkrimi.text.toString())
            intent.putExtra("dataF", holder.dataF.text.toString())
            intent.putExtra("dataM", holder.dataM.text.toString())
            intent.putExtra("doktori", holder.doktori.text.toString())
            //^vendosim te dhenat e barnes ne intent qe te mund t'e mbushim me vone editTexts
            startActivity(context, intent, null)
            //^fillo
        }
    }

    override fun getItemCount(): Int = data.size
    //^numri i barnave te regjistruara
    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var id: TextView
        var emri: TextView
        var pershkrimi: TextView
        var dataF: TextView
        var dataM: TextView
        var doktori: TextView
        var btnDel: Button
        var btnEdit: Button
        var cardItem: CardView

        //^definon antaret e nje pamjre
        init {
            cardItem = item.findViewById(R.id.cardItem)
            id = item.findViewById(R.id.tvId)
            emri = item.findViewById(R.id.tvEmri)
            pershkrimi = item.findViewById(R.id.tvPershkrimi)
            dataF = item.findViewById(R.id.tvDataF)
            dataM = item.findViewById(R.id.tvDataM)
            doktori = item.findViewById(R.id.tvDoktori)
            btnDel = item.findViewById(R.id.btnDelete)
            btnEdit = item.findViewById(R.id.btnEdit)
        }
        //^lidh antaret e nje pamje me dizajnin e percaktuar
    }
}
