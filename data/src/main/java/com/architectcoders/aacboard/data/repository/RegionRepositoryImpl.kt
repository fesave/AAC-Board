package com.architectcoders.aacboard.data.repository

import com.architectcoders.aacboard.data.datasource.local.AppPermissionChecker
import com.architectcoders.aacboard.data.datasource.local.LocationDataSource
import com.architectcoders.aacboard.domain.repository.RegionRepository

class RegionRepositoryImpl(
    private val locationDataSource: LocationDataSource,
    private val appPermissionChecker: AppPermissionChecker
) : RegionRepository {

    override suspend fun getUserLanguage(): String {
        return if (appPermissionChecker.check(AppPermissionChecker.Permission.COARSE_LOCATION)) {
            val languageByLocation = locationDataSource.getUserLanguage()?.lowercase()
                ?: ArasaacAvailableLanguages.EN.value

            ArasaacAvailableLanguages.values().first { arasaacLanguage ->
                arasaacLanguage.value == languageByLocation
            }.value

        } else {
            ArasaacAvailableLanguages.EN.value
        }
    }


    enum class ArasaacAvailableLanguages(val value: String) {
        AN("an"), AR("ar"), BG("bg"), CA("ca"), DE("de"),
        EL("el"), EN("en"), ES("es"), ET("et"), EU("eu"),
        FA("fa"), FR("fr"), GL("gl"), HE("he"), HR("hr"),
        HU("hu"), IT("it"), KO("ko"), LT("lt"), LV("lv"),
        MK("mk"), NL("nl"), PL("pl"), PT("pt"), RO("ro"),
        RU("ru"), SK("sk"), SQ("sq"), SV("sv"), SR("sr"),
        VAL("val"), UK("uk"), ZH("zh")
    }
}