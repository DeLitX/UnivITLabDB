package com.delitx.common

import com.delitx.common.db.database.Database
import com.delitx.common.db.database.DatabaseImpl
import com.delitx.common.db.row.Row
import com.delitx.common.db.row.RowImpl
import com.delitx.common.db.table.Attribute
import com.delitx.common.db.table.AttributeImpl
import com.delitx.common.db.table.Table
import com.delitx.common.db.table.TableImpl
import com.delitx.common.db.type.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull.serializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val serializer = SerializersModule {
    polymorphic(Database::class) {
        subclass(DatabaseImpl::class, DatabaseImpl.serializer())
    }
    polymorphic(Table::class) {
        subclass(TableImpl::class, TableImpl.serializer())
    }
    polymorphic(Row::class) {
        subclass(RowImpl::class, RowImpl.serializer())
    }
    polymorphic(Attribute::class) {
        subclass(AttributeImpl::class, AttributeImpl.serializer())
    }
    polymorphic(Database::class) {
        subclass(DatabaseImpl::class, DatabaseImpl.serializer())
    }
    polymorphic(Type::class) {
        subclass(TypeInt::class, TypeInt.serializer())
        subclass(TypeChar::class, TypeChar.serializer())
        subclass(TypeDate::class, TypeDate.serializer())
        subclass(TypeDateInvl::class, TypeDateInvl.serializer())
        subclass(TypeDouble::class, TypeDouble.serializer())
        subclass(TypeString::class, TypeString.serializer())
    }
}

val json = Json { serializersModule = serializer }
