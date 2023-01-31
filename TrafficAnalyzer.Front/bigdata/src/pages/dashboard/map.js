import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import {Icon} from 'leaflet';    
import { useState } from 'react';
import { useMapEvents } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import icon from 'leaflet/dist/images/marker-icon.png'


export default function Map() {

    const position = [44.793676, - 0.608054]
    const position2 = [44.795955,  -0.606845]
    const position3 = [44.798725, -0.604840]
    const position4 = [44.801930, -0.598160]
    const position5 = [44.805100, -0.601285]
    const position6 = [44.810615, -0.603550]
    const position7 = [44.812025, -0.605655]
    const position8 = [44.813300, -0.608190]
    const position9 = [44.808745, -0.596255]
    const position11 = [44.809855, -0.594220]
    const position15 = [44.804900, -0.605485]
    const position17 = [44.803100, -0.610180]
    const position19 = [44.799970, -0.616420]
    const position23 = [44.792770, -0.619310]
    const position24 = [44.791040, -0.621735]
    const position26 = [44.792600, -0.613200]
    const position10=[44.809965,- 0.595267]
    const position12=[44.809073,- 0.592110]
    const position13=[44.807627,- 0.603279]
    const position20=[44.797881,- 0.619399]

    const MarkerIcon = new Icon({
        iconUrl: icon,
        iconSize: [25, 41],
        iconAnchor: [10, 41],
        popupAnchor: [2, -40],
    })

    return (
        <MapContainer center={position15} zoom={14}
             scrollWheelZoom={true}
             style={{ height: '500px', width: '100%' }}
            >
            <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
             <Marker position={position} icon={MarkerIcon}>
             <Popup>
             <strong>Capteur : </strong>P1<br></br>
             <strong>Type : </strong>Caméra<br></br>
            <strong>Entrée : </strong>51302<br></br>
            <strong>Sortie : </strong>51189<br></br>
             </Popup>
            </Marker>
            <Marker position={position2} icon={MarkerIcon}>
             <Popup>
             <strong>Capteur : </strong>P2<br></br>
             <strong>Type : </strong>Radar TagMaster<br></br>
             </Popup>
            </Marker>
            <Marker position={position3} icon={MarkerIcon}>
             <Popup>
             <strong>Capteur : </strong>P3<br></br>
             <strong>Type : </strong>Tube Mixtra<br></br>
             </Popup>
            </Marker>

            <Marker position={position4} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P4<br></br>
             <strong>Type : </strong>Radar Viking<br></br>
                        </Popup>
                        </Marker>
            <Marker position={position5} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P5<br></br>
             <strong>Type : </strong>Radar Viking<br></br>
            <strong>Entrée : </strong>25185<br></br>
            <strong>Sortie : </strong>26273<br></br>
                        </Popup>
                        </Marker>
            <Marker position={position6} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P6<br></br>
             <strong>Type : </strong>Caméra<br></br>
            
                        </Popup>
                        </Marker>
            <Marker position={position7} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P7<br></br>
             <strong>Type : </strong>Caméra<br></br>
            
                        </Popup>
                        </Marker>
            <Marker position={position8} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P8<br></br>
             <strong>Type : </strong>Caméra<br></br>
            
                        </Popup>
                        </Marker>
            <Marker position={position9} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P9<br></br>
             <strong>Type : </strong>Tube Mixtra<br></br>
            <strong>Entrée : </strong>28642<br></br>
            <strong>Sortie : </strong>25945<br></br>
                        </Popup>
                        </Marker>
            
                        <Marker position={position10} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P10<br></br>
             <strong>Type : </strong>Caméra<br></br>
            <strong>Entrée : </strong>3361<br></br>
            <strong>Sortie : </strong>3196<br></br>
            </Popup>
            </Marker>

            <Marker position={position12} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P12<br></br>
             <strong>Type : </strong>Caméra<br></br>
            <strong>Entrée : </strong>10394<br></br>
            <strong>Sortie : </strong>10274<br></br>
            </Popup>
            </Marker>

            <Marker position={position13} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P13<br></br>
             <strong>Type : </strong>Caméra<br></br>
            <strong>Entrée : </strong>17427<br></br>
            <strong>Sortie : </strong>15559<br></br>
            </Popup>
            </Marker>

            <Marker position={position11} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P11<br></br>
             <strong>Type : </strong>Tube Mixtra<br></br>
            
                        </Popup>
                        </Marker>
            <Marker position={position15} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P15<br></br>
             <strong>Type : </strong>Delta<br></br>
            
                        </Popup>
                        </Marker>
            <Marker position={position17} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P17<br></br>
             <strong>Type : </strong>Radar Viking<br></br>
            <strong>Entrée : </strong>84252<br></br>
            <strong>Sortie : </strong>96298<br></br>
                        </Popup>
                        </Marker>
            <Marker position={position19} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P19<br></br>
             <strong>Type : </strong>Tube Mixtra<br></br>
            <strong>Entrée : </strong>12228<br></br>
            <strong>Sortie : </strong>14401<br></br>
                        </Popup>
                        </Marker>

                        <Marker position={position20} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P20<br></br>
             <strong>Type : </strong>Caméra<br></br>
            <strong>Entrée : </strong>6278<br></br>
            <strong>Sortie : </strong>4353<br></br>
                        </Popup>
                        </Marker>


            <Marker position={position23} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P23<br></br>
             <strong>Type : </strong>Tube Mixtra<br></br>
            <strong>Entrée : </strong>28503<br></br>
            <strong>Sortie : </strong>17052<br></br>
                        </Popup>
                        </Marker>
            <Marker position={position24} icon={MarkerIcon}>
        
            <Popup>
            <strong>Capteur : </strong>P24<br></br>
             <strong>Type : </strong>Tube Mixtra<br></br>
             <strong>Entrée : </strong>13119<br></br>
            <strong>Sortie : </strong>17796<br></br>
                        </Popup>
                        </Marker>
            <Marker position={position26} icon={MarkerIcon}>
                        <Popup>
                        <strong>Capteur : </strong>P26<br></br>
             <strong>Type : </strong>Tube Mixtra<br></br>
            <strong>Entrée : </strong>48929<br></br>
            <strong>Sortie : </strong>30610<br></br>
                        </Popup>
                        </Marker>

            
        </MapContainer>
    )
}