using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IEntity
    {
        Guid Uid { get; set; }
    }
}
