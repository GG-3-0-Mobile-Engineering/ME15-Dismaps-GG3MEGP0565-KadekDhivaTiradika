package com.bydhiva.dismaps.dummy

import com.bydhiva.dismaps.data.network.response.ReportsResponse
import com.google.gson.Gson

class DummyReportsResponse {
    companion object {
        private const val responseBody200 = "{\n" +
                "    \"statusCode\": 200,\n" +
                "    \"result\": {\n" +
                "        \"type\": \"Topology\",\n" +
                "        \"objects\": {\n" +
                "            \"output\": {\n" +
                "                \"type\": \"GeometryCollection\",\n" +
                "                \"geometries\": [\n" +
                "                    {\n" +
                "                        \"type\": \"Point\",\n" +
                "                        \"properties\": {\n" +
                "                            \"pkey\": \"321763\",\n" +
                "                            \"created_at\": \"2023-08-01T15:47:16.928Z\",\n" +
                "                            \"source\": \"grasp\",\n" +
                "                            \"status\": \"confirmed\",\n" +
                "                            \"url\": \"552dc58f-7ea6-433f-a6ff-6f22a9a5251a\",\n" +
                "                            \"image_url\": \"https://images.petabencana.id/552dc58f-7ea6-433f-a6ff-6f22a9a5251a.jpg\",\n" +
                "                            \"disaster_type\": \"flood\",\n" +
                "                            \"report_data\": {\n" +
                "                                \"report_type\": \"flood\",\n" +
                "                                \"flood_depth\": 194\n" +
                "                            },\n" +
                "                            \"tags\": {\n" +
                "                                \"district_id\": null,\n" +
                "                                \"region_code\": \"3201\",\n" +
                "                                \"local_area_id\": null,\n" +
                "                                \"instance_region_code\": \"ID-JB\"\n" +
                "                            },\n" +
                "                            \"title\": null,\n" +
                "                            \"text\": \"#Trainer Deden Fadilah\",\n" +
                "                            \"partner_code\": null,\n" +
                "                            \"partner_icon\": null\n" +
                "                        },\n" +
                "                        \"coordinates\": [\n" +
                "                            106.8945294783,\n" +
                "                            -6.5444890526\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        },\n" +
                "        \"arcs\": [],\n" +
                "        \"bbox\": [\n" +
                "            106.8945294783,\n" +
                "            -6.5444890526,\n" +
                "            106.8945294783,\n" +
                "            -6.5444890526\n" +
                "        ]\n" +
                "    }\n" +
                "}"
        val reportsResponse200: ReportsResponse = Gson().fromJson(responseBody200, ReportsResponse::class.java)
        private const val responseBody500 = "{ \"statusCode\": 500 }"
        val reportsResponse500: ReportsResponse = Gson().fromJson(responseBody500, ReportsResponse::class.java)
        private const val responseBodyEmpty200 = "{\n" +
                "    \"statusCode\": 200,\n" +
                "    \"result\": {\n" +
                "        \"type\": \"Topology\",\n" +
                "        \"objects\": {\n" +
                "            \"output\": {\n" +
                "                \"type\": \"GeometryCollection\",\n" +
                "                \"geometry\": []\n" +
                "            }\n" +
                "        },\n" +
                "        \"arcs\": [],\n" +
                "        \"bbox\": [\n" +
                "            106.8945294783,\n" +
                "            -6.5444890526,\n" +
                "            106.8945294783,\n" +
                "            -6.5444890526\n" +
                "        ]\n" +
                "    }\n" +
                "}"
        val reportsResponseEmpty200: ReportsResponse = Gson().fromJson(responseBodyEmpty200, ReportsResponse::class.java)
    }
}