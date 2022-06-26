package com.dechenkov.gitviewer.modules.details_repositories.domain.usecases

import com.dechenkov.gitviewer.shared.app.IAppRepository
import javax.inject.Inject

class EditReadmeUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(owner: String, repository: String, readme: String): String {
        val uris = parseLocalUris(readme)
        val readmeBuild = StringBuilder(readme)

        uris.forEach {
            val index = readmeBuild.indexOf(it.entry)
            if (index == -1)
                return@forEach

            val uri = appRepository.getUrlImage(owner, repository, it.uri)
            val length = it.entry.length
            val image = it.entry.replace(it.uri, uri)

            readmeBuild.replace(index, index + length, image)
        }

        return readmeBuild.toString()
    }

    private fun parseLocalUris(document: String): Set<UriPair> {
        val subRegex = Regex("""((\w*|[_,-,\,.]*)\/)*(\w*|[_,-,\,.])\.(png|jpg|jpeg|gif)""")
        val regex =
            Regex("""(src=\"${subRegex.pattern}\")|(!\[.*\]\(${subRegex.pattern}\))""")

        return regex.findAll(document).map {
            UriPair(
                entry = it.value,
                uri = subRegex.find(it.value)!!.value
            )
        }.toSet()
    }

    data class UriPair(
        val entry: String,
        val uri: String
    )
}